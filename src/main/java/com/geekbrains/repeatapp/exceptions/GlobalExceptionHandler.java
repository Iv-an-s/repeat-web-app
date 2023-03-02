package com.geekbrains.repeatapp.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Перехватчик указанных исключений для всех контроллеров
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Метод с данной аннотацией (@ExceptionHandler), если разместить в каком-либо контроллере, будет перехватывать
     * исключения указанного типа (например ResourceNotFoundException), возникающие в данном контроллере, в любом из методов.
     * Если хотим чтобы перехват данного исключения производился во всех контроллерах, размещаем его здесь, в бине
     * с аннотацией @ControllerAdvice.
     *
     * @param e
     * @return
     */
    @ExceptionHandler
    public ResponseEntity<?> catchResourceNotFoundException(ResourceNotFoundException e){
        return new ResponseEntity<>(new MarketError(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<?> catchDataValidationException(DataValidationException e){
        return new ResponseEntity<>(new MarketError(e.getMessages()), HttpStatus.BAD_REQUEST);
    }

}
