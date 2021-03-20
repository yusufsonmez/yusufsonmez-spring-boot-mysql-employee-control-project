package com.luv2code.springboot.thymeleafdemo.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.luv2code.springboot.thymeleafdemo.service.UserService;
//import com.luv2code.springsecurity.demo.config.CustomAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class DemoSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserService userService;
	
	// add a reference to our security data source
	
	@Autowired
	@Qualifier("securityDataSource")
	private DataSource securityDataSource;
		
	 @Autowired
	    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
	 
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		// use jdbc authentication ... 
		auth.jdbcAuthentication().dataSource(securityDataSource);
        auth.authenticationProvider(authenticationProvider());


		
	}   
	 
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
//
		http.authorizeRequests()
			.antMatchers("/employees/showForm*").hasAnyRole("MANAGER", "ADMIN")
			.antMatchers("/employees/save*").hasAnyRole("MANAGER", "ADMIN")
			.antMatchers("/employees/delete").hasRole("ADMIN")
			.antMatchers("/employees/**").hasRole("EMPLOYEE")
			.antMatchers("/resources/**").permitAll()
			.and()
			.formLogin()
				.loginPage("/showMyLoginPage")
				.loginProcessingUrl("/authenticateTheUser")
				.successHandler(customAuthenticationSuccessHandler)
				.permitAll()
			.and()
			.logout().permitAll()
			.and()
			.exceptionHandling().accessDeniedPage("/access-denied");

	}
		
	//beans
	//bcrypt bean definition
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	//authenticationProvider bean definition
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(userService); //set the custom user details service
		auth.setPasswordEncoder(passwordEncoder()); //set the password encoder - bcrypt
		return auth;
	}
	
}






