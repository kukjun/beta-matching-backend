package io.wisoft.testermatchingplatform.handler;

import io.wisoft.testermatchingplatform.handler.exception.domain.*;
import io.wisoft.testermatchingplatform.handler.exception.service.*;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmptyAccountException.class)
    public ResponseEntity<ErrorResponse> emptyAccount(final EmptyAccountException e) {
        ErrorResponse errorResponse = generateErrorResponseWithMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(InsufficientPointException.class)
    public ResponseEntity<ErrorResponse> insufficientPoint(final InsufficientPointException e) {
        ErrorResponse errorResponse = generateErrorResponseWithMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(MissionDateCurrentTimeBeforeApplyException.class)
    public ResponseEntity<ErrorResponse> missionDateCurrentTimeBeforeApply(final MissionDateCurrentTimeBeforeApplyException e) {
        ErrorResponse errorResponse = generateErrorResponseWithMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(MissionDateMismatchException.class)
    public ResponseEntity<ErrorResponse> missionDateMismatch(final MissionDateMismatchException e) {
        ErrorResponse errorResponse = generateErrorResponseWithMessage(e.getMessage());
        // 비지니스 상 모순이 발생햇으므로 Conflict
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(MissionStatusMismatchException.class)
    public ResponseEntity<ErrorResponse> missionStatusMismatch(final MissionStatusMismatchException e) {
        ErrorResponse errorResponse = generateErrorResponseWithMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(MismatchPasswordException.class)
    public ResponseEntity<ErrorResponse> mismatchPassword(final MismatchPasswordException e) {
        ErrorResponse errorResponse = generateErrorResponseWithMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(NotApplyPeriodException.class)
    public ResponseEntity<ErrorResponse> notApplyPeriodException(final InsufficientPointException e) {
        ErrorResponse errorResponse = generateErrorResponseWithMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(NotNaturalNumberException.class)
    public ResponseEntity<ErrorResponse> notNaturalNumber(final NotNaturalNumberException e) {
        ErrorResponse errorResponse = generateErrorResponseWithMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(ApplyInformationNotFoundException.class)
    public ResponseEntity<ErrorResponse> applyInformationNotFound(final ApplyInformationNotFoundException e) {
        ErrorResponse errorResponse = generateErrorResponseWithMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ExceptionHandler(MakerNotFoundException.class)
    public ResponseEntity<ErrorResponse> makerNotFound(final MakerNotFoundException e) {
        ErrorResponse errorResponse = generateErrorResponseWithMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ExceptionHandler(MakerReviewNotFoundException.class)
    public ResponseEntity<ErrorResponse> makerReviewNotFound(final MakerNotFoundException e) {
        ErrorResponse errorResponse = generateErrorResponseWithMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ExceptionHandler(MissionNotFoundException.class)
    public ResponseEntity<ErrorResponse> missionNotFound(final MissionNotFoundException e) {
        ErrorResponse errorResponse = generateErrorResponseWithMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ExceptionHandler(TesterNotFoundException.class)
    public ResponseEntity<ErrorResponse> testerNotFound(final TesterNotFoundException e) {
        ErrorResponse errorResponse = generateErrorResponseWithMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ExceptionHandler(TesterReviewNotFoundException.class)
    public ResponseEntity<ErrorResponse> testerReviewNotFound(final TesterReviewNotFoundException e) {
        ErrorResponse errorResponse = generateErrorResponseWithMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }


    private ErrorResponse generateErrorResponseWithMessage(String errorMessage) {
        final ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.add(errorMessage);
        return errorResponse;
    }

}

@Getter
class ErrorResponse {
    final List<String> message;

    public ErrorResponse() {
        this(new ArrayList<>());
//        this.message = new ArrayList<>();
    }
    public ErrorResponse(List<String> message) {
        this.message = message;
    }

    void add(String message) {
        this.message.add(message);
    }
}
