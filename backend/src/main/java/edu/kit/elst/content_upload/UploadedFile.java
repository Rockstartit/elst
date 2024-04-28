package edu.kit.elst.content_upload;

import edu.kit.elst.core.shared.FileId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "uploaded_files")
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class UploadedFile {
    @EmbeddedId
    private FileId id;

    private String name;
    private String type;

    @Lob
    private byte[] data;

    public UploadedFile(String name, String type, byte[] data) {
        this.id = UploadedFiles.nextIdentity();
        this.name = name;
        this.type = type;
        this.data = data;
    }
}
