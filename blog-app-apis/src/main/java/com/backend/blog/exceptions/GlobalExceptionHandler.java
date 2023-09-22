package com.backend.blog.exceptions;


import com.backend.blog.payloads.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
// @ResponseBody + @ControllerAdvice
// @ControllerAdvice -> @ExceptionHandler, @InitBinder, @ModelAttribute
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
        ApiResponse apiResponse = new ApiResponse(ex.getMessage(),false);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> methodArgumentExceptionHandler(MethodArgumentNotValidException ex){
         Map<String,String> response = new HashMap<>();
         ex.getBindingResult().getAllErrors().forEach(
                 x->{
                     response.put(((FieldError)x).getField(),x.getDefaultMessage());
                 }
         );
        return new ResponseEntity<Map<String,String>>(response,HttpStatus.BAD_REQUEST);
    }
}
