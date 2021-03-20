package com.luv2code.springboot.thymeleafdemo.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

//import org.apache.tomcat.jni.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.luv2code.springboot.thymeleafdemo.entity.Employee;
import com.luv2code.springboot.thymeleafdemo.entity.Review;
import com.luv2code.springboot.thymeleafdemo.entity.Role;
import com.luv2code.springboot.thymeleafdemo.service.EmployeeService;
import com.luv2code.springboot.thymeleafdemo.service.UserService;
//import com.luv2code.springdemo.thymeleafdemo.exceptionHandling.CustomerNotFoundException;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

	
//	<!-- Userlar birbirlerine mesaj atsın -->

	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private UserService userService;
	
	public EmployeeController(EmployeeService theEmployeeService) {
		employeeService = theEmployeeService;
	}
	
	@GetMapping("/list")
	public String listEmployees(Model theModel) {
		
		// get employees from db
		List<Employee> theEmployees = employeeService.findAll();
		
		// add to the spring model
		theModel.addAttribute("employees", theEmployees);
		
		return "/employees/list-employees";
	}
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		
		// create model attribute to bind form data
		Employee theEmployee = new Employee();
		
		theModel.addAttribute("employee", theEmployee);
		
		return "/employees/employee-form";
	}

	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("employeeId") int theId,
									Model theModel) {
		
		// get the employee from the service
		Employee theEmployee = employeeService.findById(theId);
		
		// set employee as a model attribute to pre-populate the form
		theModel.addAttribute("employee", theEmployee);
		
		// send over to our form
		return "/employees/employee-form";			
	}
	
	
	@PostMapping("/save")
	public String saveEmployee(@ModelAttribute("employee") Employee theEmployee) {
		
		// save the employee
		employeeService.save(theEmployee);
		
		// use a redirect to prevent duplicate submissions
		return "redirect:/employees/list";
	}
	
	
	@GetMapping("/delete")
	public String delete(@RequestParam("employeeId") int theId) {
		
		// delete the employee
		employeeService.deleteById(theId);
		
		// redirect to /employees/list
		return "redirect:/employees/list";
		
	}
	
	@GetMapping("/search")
	public String search(@RequestParam("employeeName") String theName,
						 Model theModel) {
		
		// search the employee
		List<Employee> theEmployees = employeeService.searchBy(theName);
		
		// add to the spring model
		theModel.addAttribute("employees", theEmployees);
		
		// send to /employees/list
		return "/employees/list-employees";
		
	}
	
	@GetMapping("/searchById")
	public String searchById(@RequestParam("employeeId") int theId,
						 Model theModel) {
		// search the employee by id
		Employee theEmployees = employeeService.findById(theId);
		
		if(theEmployees == null) {
//			throw new EmployeeNotFoundException("Customer id not found - " + theId);
			throw new EmployeeNotFoundException();

		}
			
			// add to the spring model
			theModel.addAttribute("employees", theEmployees);
		
		
		// send to /employees/list
		return "/employees/list-employees";
		
	}
	

	@GetMapping("/reviews")
	public String getReviews(Model theModel) {
		
		
		Authentication authentication =
				SecurityContextHolder.getContext().getAuthentication();
		
		org.springframework.security.core.userdetails.User theUser = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
		String userName = theUser.getUsername();
		//custom username'i aldık. Yani hangi userla girilmisse onun username'i
		
		System.out.println(userName);
		
		com.luv2code.springboot.thymeleafdemo.entity.User user = userService.findByUserName(userName);
		
		long theId = user.getId();
		//ilgili user'ın id'sini aldık. Sonra da o id'ye göre reviewsleri getiriyoruz asagida
		
		Collection<Role> userRoles = user.getRoles();
		
		Collection<Role> userRolesArrayForUserAuthorization= new ArrayList<Role>();
		userRolesArrayForUserAuthorization.addAll( userRoles);



		// eger role admin ise her seyi gorsun degilse idlerine gore girilen idleri gorsun
		
				for(Role role : userRolesArrayForUserAuthorization) {
					System.out.println(role.getName());
					if(role.getName().equals("ROLE_ADMIN")) {
						List<Review> theReviews = userService.getReview();
						theModel.addAttribute("reviews", theReviews);
					}
					else {
						List<Review> theReviews = userService.getReviewById(theId);
						theModel.addAttribute("reviews", theReviews);
					}
				}
		
		
		return "/employees/list-employees";
	}

	
	@PostMapping("/reviews")
	public String createReview(@ModelAttribute("review") Review theReview) {

		
		userService.createReview(theReview);
		return "redirect:/employees/list";
		
	}
	
}




