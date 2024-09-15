package ru.clevertec.crudspringtestproject.exception.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.clevertec.crudspringtestproject.exception.CannotDeletePhoneException;
import ru.clevertec.crudspringtestproject.exception.PhoneExistException;
import ru.clevertec.crudspringtestproject.exception.PhoneNotFoundException;
import ru.clevertec.crudspringtestproject.exception.UserNotFoundException;
import ru.clevertec.crudspringtestproject.utils.DataTimeUtil;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final String ERROR_OCCURRED_IN_METHOD = "Error occurred in method: ";
    private static final String IN_FILE_NAME = " in file name: ";
    private final DataTimeUtil dataTimeUtil;

    @ExceptionHandler(CannotDeletePhoneException.class)
    public ResponseEntity<Object> handleCannotDeletePhoneException(CannotDeletePhoneException e) {
        String errorDetails = ERROR_OCCURRED_IN_METHOD + e.getStackTrace()[0].getMethodName()
                + IN_FILE_NAME + e.getStackTrace()[1].getFileName();
        ErrorDetails response = new ErrorDetails(dataTimeUtil.localDateTimeFormatter(LocalDateTime.now()),
                e.getMessage(), errorDetails);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PhoneExistException.class)
    public ResponseEntity<Object> handlePhoneExistExceptionException(PhoneExistException e) {
        String errorDetails = ERROR_OCCURRED_IN_METHOD + e.getStackTrace()[0].getMethodName()
                + IN_FILE_NAME + e.getStackTrace()[1].getFileName();
        ErrorDetails response = new ErrorDetails(dataTimeUtil.localDateTimeFormatter(LocalDateTime.now()),
                e.getMessage(), errorDetails);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PhoneNotFoundException.class)
    public ResponseEntity<Object> handlePhoneNotFoundException(PhoneNotFoundException e) {
        String errorDetails = ERROR_OCCURRED_IN_METHOD + e.getStackTrace()[0].getMethodName()
                + IN_FILE_NAME + e.getStackTrace()[1].getFileName();
        ErrorDetails response = new ErrorDetails(dataTimeUtil.localDateTimeFormatter(LocalDateTime.now()),
                e.getMessage(), errorDetails);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException e) {
        String errorDetails = ERROR_OCCURRED_IN_METHOD + e.getStackTrace()[0].getMethodName()
                + IN_FILE_NAME + e.getStackTrace()[1].getFileName();
        ErrorDetails response = new ErrorDetails(dataTimeUtil.localDateTimeFormatter(LocalDateTime.now()),
                e.getMessage(), errorDetails);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}