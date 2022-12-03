package com.nhnacademy.jpa.family.controller.error;



import com.nhnacademy.jpa.family.exception.ValidationFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.SessionAttribute;

@Slf4j
@ControllerAdvice
public class WebControllerAdvice {

    private final String EXCEPTION = "exception";
    private final String THYMELEAF_ERROR = "error/error";
//
//    @ExceptionHandler(LoginInfoNotFoundException.class)
//    public String handleLoginInfoNotFoundException(LoginInfoNotFoundException ex, Model model) {
//        model.addAttribute("loginInfo", ex.getMessage());
//        return "index/index";
//    }
//
//    @ExceptionHandler(UserNotFoundException.class)
//    public String handleUserNotFoundException(UserNotFoundException ex, Model model) {
//        model.addAttribute(EXCEPTION, ex);
//        return THYMELEAF_ERROR;
//    }
//
//    @ExceptionHandler(BoardNotFoundException.class)
//    public String handleBoardNotFoundException(BoardNotFoundException ex, Model model) {
//        model.addAttribute("boardInfo", ex.getMessage());
//        return "board/boardList";
//    }

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
