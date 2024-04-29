package edu.kit.elst.rest_api;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@AllArgsConstructor
public class FileController implements FileApi {
    @Override
    public ResponseEntity<Object> downloadFile(UUID fileId) {
        return FileApi.super.downloadFile(fileId);
    }
}
