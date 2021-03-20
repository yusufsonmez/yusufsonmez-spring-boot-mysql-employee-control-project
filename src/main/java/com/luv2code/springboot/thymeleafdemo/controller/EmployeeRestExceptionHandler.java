package com.luv2code.springboot.thymeleafdemo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.luv2code.springboot.thymeleafdemo.message.ResponseMessage;

@ControllerAdvice
public class EmployeeRestExceptionHandler {

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<ResponseMessage> handleMaxSizeException(MaxUploadSizeExceededException exc) {
	    return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage("File too large!"));
	  }
	
	//Add an exception handler for CustomerNotFoundException
	@ExceptionHandler
	public ResponseEntity<EmployeeErrorResponse> handleException(EmployeeNotFoundException exc){
		
		//create CustomerErrorResponse
		EmployeeErrorResponse error = new EmployeeErrorResponse(HttpStatus.NOT_FOUND.value(),
																		exc.getMessage(),
																		System.currentTimeMillis());
		
		//return ResponseEntity
		
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public ResponseEntity<EmployeeErrorResponse> handleException(Exception exc){
		
		//create CustomerErrorResponse
//		EmployeeErrorResponse error = new EmployeeErrorResponse(HttpStatus.BAD_REQUEST.value(),
//																		exc.getMessage(),
//																		System.currentTimeMillis());
		
		EmployeeErrorResponse error = new EmployeeErrorResponse();
		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setMessage(exc.getMessage());
//		error.setMessage("Aramaniz bulunamadi");
		error.setTimeStamp(System.currentTimeMillis());
		
		//return ResponseEntity
		
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	
	//Add another exception handler.. to catch any exception(catch all)
	
	
}
