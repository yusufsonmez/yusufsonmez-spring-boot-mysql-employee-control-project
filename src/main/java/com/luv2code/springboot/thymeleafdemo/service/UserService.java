package com.luv2code.springboot.thymeleafdemo.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.luv2code.springboot.thymeleafdemo.entity.Review;
import com.luv2code.springboot.thymeleafdemo.entity.User;
import com.luv2code.springboot.thymeleafdemo.user.CrmUser;

public interface UserService extends UserDetailsService {

	public User findByUserName(String userName);

	public void save(CrmUser crmUser);
		
	public String createReview(Review theReview);

	List<Review> getReviewById(long theIdd);

	public List<Review> getReview();


}
