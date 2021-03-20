package com.luv2code.springboot.thymeleafdemo.dao;

import java.util.List;

import com.luv2code.springboot.thymeleafdemo.entity.Review;
import com.luv2code.springboot.thymeleafdemo.entity.User;

public interface UserDao {

    public User findByUserName(String userName);
    
    public void save(User user);
    
	public String createReview(Review theReview);

	public List<Review> getReviewById(long theId);

	public List<Review> getReview();
    
}
