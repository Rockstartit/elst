package edu.kit.elst.lesson_planning;

import edu.kit.elst.core.shared.FileId;
import edu.kit.elst.core.Guards;
import edu.kit.elst.core.shared.LearningMaterialId;
import edu.kit.elst.core.shared.TeachingPhaseId;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "learning_materials")
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class LearningMaterial {
    @EmbeddedId
    private final LearningMaterialId id;

    @Getter(AccessLevel.NONE)
    @JoinColumn(name = "teaching_phase_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private final TeachingPhase teachingPhase;

    private final FileId fileId;

    private String name;

    @Lob
    @Column(length = 256)
    private String description;

    public LearningMaterial(TeachingPhase teachingPhase, FileId fileId, String name) {
        Guards.notNull(teachingPhase, "teachingPhase");
        Guards.notNull(fileId, "fileId");

        this.id = LearningMaterials.nextIdentity();
        this.teachingPhase = teachingPhase;
        this.fileId = fileId;

        name(name);
    }

    public TeachingPhaseId teachingPhaseId() {
        return teachingPhase.id();
    }

    public void name(String name) {
        Guards.notEmptyBlankOrNull(name, "name");

        this.name = name;
    }
}
