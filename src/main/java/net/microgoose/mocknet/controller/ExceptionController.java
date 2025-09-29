package net.microgoose.mocknet.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.microgoose.mocknet.dto.ErrorResponse;
import net.microgoose.mocknet.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionController {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleBadRequest(ValidationException ex, HttpServletRequest request) {
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleBadRequest(Exception ex, HttpServletRequest request) {
        return buildErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, ErrorResponse.builder()
            .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .message("Внутреняя ошибка сервера")
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
