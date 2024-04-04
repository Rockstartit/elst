package edu.kit.elst.course_implementation;

import edu.kit.elst.building_blocks.BuildingBlockVersion;
import edu.kit.elst.core.Guards;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.nio.charset.StandardCharsets;

@Entity
@Getter
@Table(name = "readme")
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class ReadMe {
    @EmbeddedId
    private final BuildingBlockVersion buildingBlockVersion;

    @Lob
    private byte[] content;

    public ReadMe(BuildingBlockVersion buildingBlockVersion) {
        this.buildingBlockVersion = buildingBlockVersion;
        this.content = new byte[0];
    }

    public void data(String content) {
        Guards.notNull(content, "content");

        this.content = content.getBytes(StandardCharsets.UTF_8);
    }

    public String content() {
        return new String(content);
    }
}
