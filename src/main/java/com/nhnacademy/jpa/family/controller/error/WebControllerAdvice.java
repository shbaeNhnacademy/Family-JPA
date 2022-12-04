package com.nhnacademy.jpa.family.controller.error;


import com.nhnacademy.jpa.family.exception.ValidationFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class WebControllerAdvice {

    private final String EXCEPTION = "exception";
    private final String THYMELEAF_ERROR = "error/error";

    @ExceptionHandler(ValidationFailedException.class)
    public ResponseEntity<String> handleValidationFailedException(ValidationFailedException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        log.error("", ex);

        model.addAttribute(EXCEPTION, ex);
        return THYMELEAF_ERROR;
    }

}
