package io.wisoft.testermatchingplatform.web.controller;

import io.wisoft.testermatchingplatform.handler.FileHandler;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/image")
public class ImageController {

    @GetMapping("/{mission_representation_image}")
    public ResponseEntity<byte[]> getMissionRepresentationImage(
            @PathVariable("mission_representation_image") final String missionRepresentationImage
    ) {
        final byte[] fileData = FileHandler.getMissionRepresentationFileData(missionRepresentationImage);
        final String contentType = FileHandler.getImageContentType(missionRepresentationImage);
        final HttpHeaders headers = generateContentTypeHeader(contentType);

        return new ResponseEntity<>(
                fileData, headers, HttpStatus.OK);
    }

    private HttpHeaders generateContentTypeHeader(final String contentType) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(contentType));

        return headers;
    }

}
