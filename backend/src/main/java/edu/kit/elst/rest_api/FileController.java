package edu.kit.elst.rest_api;

import edu.kit.elst.content_upload.StorageService;
import edu.kit.elst.content_upload.UploadedFile;
import edu.kit.elst.core.shared.FileId;
import lombok.AllArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class FileController implements FileApi {
    private final StorageService storageService;

    @Override
    public ResponseEntity<byte[]> downloadFile(UUID fileId) {
        FileId aFileId = new FileId(fileId);

        UploadedFile uploadedFile = storageService.file(aFileId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentDisposition(
                ContentDisposition.attachment()
                        .filename(uploadedFile.name(), StandardCharsets.UTF_8)
                        .name(uploadedFile.name())
                        .build());

        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(uploadedFile.data());
    }
}
