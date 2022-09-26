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

    @GetMapping("/api/image/{test_representation_image}")
    public ResponseEntity<byte[]> getTestRepresentationImage(
            @PathVariable("test_representation_image") final String testRepresentationImage
    ) {

        final byte[] fileData = FileHandler.getTestRepresentationFileData(testRepresentationImage);
        final String contentType = FileHandler.getImageContentType(testRepresentationImage);
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
