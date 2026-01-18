package net.sobestime.app.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sobestime.app.dto.ErrorResponse;
import net.sobestime.app.exception.IllegalActionException;
import net.sobestime.app.exception.NotFoundException;
import net.sobestime.app.exception.ValidationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionController {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleValidationError(ValidationException ex, HttpServletRequest request) {
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleValidationError(MethodArgumentNotValidException ex, HttpServletRequest request) {
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, ErrorResponse.builder()
            .code(HttpStatus.BAD_REQUEST.value())
            .message("Некорректный запрос")
            .timestamp(OffsetDateTime.now())
            .build());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleValidationError(InvalidDataAccessApiUsageException ex, HttpServletRequest request) {
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, ErrorResponse.builder()
            .code(HttpStatus.BAD_REQUEST.value())
            .message("Некорректный запрос")
            .timestamp(OffsetDateTime.now())
            .build());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleNotFoundError(NotFoundException ex, HttpServletRequest request) {
        return buildErrorResponse(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleIllegalActionError(IllegalActionException ex, HttpServletRequest request) {
        return buildErrorResponse(ex, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleUnknownError(Exception ex, HttpServletRequest request) {
        return buildErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, ErrorResponse.builder()
            .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .message("Внутренняя ошибка сервера")
            .timestamp(OffsetDateTime.now())
            .build());
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(Exception ex, HttpStatus status) {
        ErrorResponse errorResponse = ErrorResponse.builder()
            .code(status.value())
            .message(ex.getMessage())
            .timestamp(OffsetDateTime.now())
            .build();

        return buildErrorResponse(ex, status, errorResponse);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(Exception ex, HttpStatus status, ErrorResponse errorResponse) {
        log.error("Ответ с ошибкой:", ex);

        return ResponseEntity
            .status(status)
            .body(errorResponse);
    }

}
