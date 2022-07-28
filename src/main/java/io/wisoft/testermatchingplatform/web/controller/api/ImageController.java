package io.wisoft.testermatchingplatform.web.controller.api;

import io.wisoft.testermatchingplatform.handler.FileHandler;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageController {

    @GetMapping("/api/profile/{profile_image_reference}")
    public ResponseEntity<byte[]> profileImage(
            @PathVariable("profile_image_reference") final String profileImageName
    ) {

        final byte[] fileData = FileHandler.getProfileFileData(profileImageName);
        final String contentType = FileHandler.getImageContentType(profileImageName);
        final HttpHeaders headers = generateContentTypeHeader(contentType);

        return new ResponseEntity<>(
                fileData, headers, HttpStatus.OK
        );
    }

    @GetMapping("/api/apply/require/{require_image_reference}")
    public ResponseEntity<byte[]> requireImage(
            @PathVariable("require_image_reference") final String applyRequireImageName
    ) {
        final byte[] fileData = FileHandler.getApplyRequireFileData(applyRequireImageName);
        final String contentType = FileHandler.getImageContentType(applyRequireImageName);
        final HttpHeaders headers = generateContentTypeHeader(contentType);

        return new ResponseEntity<>(
                fileData, headers, HttpStatus.OK
        );
    }

    @GetMapping("/api/apply/prefer/{prefer_image_reference}")
    public ResponseEntity<byte[]> preferImage(
            @PathVariable("prefer_image_reference") final String applyPreferImageName
    ) {
        final byte[] fileData = FileHandler.getApplyPreferFileData(applyPreferImageName);
        final String contentType = FileHandler.getImageContentType(applyPreferImageName);
        final HttpHeaders headers = generateContentTypeHeader(contentType);

        return new ResponseEntity<>(
                fileData, headers, HttpStatus.OK
        );
    }

    private HttpHeaders generateContentTypeHeader(final String contentType) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(contentType));

        return headers;
    }
}
