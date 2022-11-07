package io.wisoft.testermatchingplatform.handler;

import io.wisoft.testermatchingplatform.handler.exception.auth.EmailOverlapException;
import io.wisoft.testermatchingplatform.handler.exception.auth.NicknameOverlapException;
import io.wisoft.testermatchingplatform.handler.exception.auth.PhoneNumberOverlapException;
import io.wisoft.testermatchingplatform.handler.exception.maker.*;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailOverlapException.class)
    public ResponseEntity<ErrorResponse> emailOverlap(final EmailOverlapException e) {
        ErrorResponse errorResponse = generateErrorResponseWithMessage(e.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }

    @ExceptionHandler(NicknameOverlapException.class)
    public ResponseEntity<ErrorResponse> nicknameOverlap(final NicknameOverlapException e) {
        ErrorResponse errorResponse = generateErrorResponseWithMessage(e.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }

    @ExceptionHandler(PhoneNumberOverlapException.class)
    public ResponseEntity<ErrorResponse> phoneNumberOverlap(final PhoneNumberOverlapException e) {
        ErrorResponse errorResponse = generateErrorResponseWithMessage(e.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }

    @ExceptionHandler(MakerCreateTestFailException.class)
    public ResponseEntity<ErrorResponse> createTestFail(final MakerCreateTestFailException e) {
        ErrorResponse errorResponse = generateErrorResponseWithMessage(e.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }

    @ExceptionHandler(MakerRevertTestFailException.class)
    public ResponseEntity<ErrorResponse> revertTestFail(final MakerRevertTestFailException e) {
        ErrorResponse errorResponse = generateErrorResponseWithMessage(e.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }

    @ExceptionHandler(MakerApproveOverlapException.class)
    public ResponseEntity<ErrorResponse> approveOverlap(final MakerApproveOverlapException e) {
        ErrorResponse errorResponse = generateErrorResponseWithMessage(e.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }

    @ExceptionHandler(MakerCompleteOverlapException.class)
    public ResponseEntity<ErrorResponse> completeOverlap(final MakerCompleteOverlapException e) {
        ErrorResponse errorResponse = generateErrorResponseWithMessage(e.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }

    @ExceptionHandler(MakerReviewOverlapException.class)
    public ResponseEntity<ErrorResponse> reviewOverlap(final MakerReviewOverlapException e) {
        ErrorResponse errorResponse = generateErrorResponseWithMessage(e.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT)
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
