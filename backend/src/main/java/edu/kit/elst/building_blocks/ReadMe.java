package edu.kit.elst.building_blocks;

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
    private final BuildingBlockId buildingBlockId;

    @Lob
    private byte[] content;

    public ReadMe(BuildingBlockId buildingBlockId) {
        this.buildingBlockId = buildingBlockId;
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
