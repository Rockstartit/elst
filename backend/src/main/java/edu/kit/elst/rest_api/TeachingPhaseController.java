package edu.kit.elst.rest_api;

import edu.kit.elst.lesson_planning.TeachingPhaseAppService;
import edu.kit.elst.core.shared.TeachingPhaseId;
import edu.kit.elst.core.shared.TeachingUnitId;
import edu.kit.elst.lesson_planning.Topic;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class TeachingPhaseController implements TeachingPhaseApi {
    private final TeachingPhaseAppService teachingPhaseAppService;

    @Override
    public ResponseEntity<UUID> createTeachingPhase(UUID teachingUnitId, CreateTeachingPhaseRequest body) {
        TeachingUnitId aTeachingUnitId = new TeachingUnitId(teachingUnitId);
        Topic topic = new Topic(body.getTopic());

        TeachingPhaseId teachingPhaseId = teachingPhaseAppService.createTeachingPhase(aTeachingUnitId, topic);

        return ResponseEntity.ok(teachingPhaseId.value());
    }

    @Override
    public ResponseEntity<Void> deleteTeachingPhase(UUID teachingPhaseId) {
        TeachingPhaseId aTeachingPhaseId = new TeachingPhaseId(teachingPhaseId);

        teachingPhaseAppService.deleteTeachingPhase(aTeachingPhaseId);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> editTeachingPhase(UUID teachingPhaseId, EditTeachingPhaseRequest body) {
        TeachingPhaseId aTeachingPhaseId = new TeachingPhaseId(teachingPhaseId);

        if (body.getTopic() != null) {
            Topic topic = new Topic(body.getTopic());

            teachingPhaseAppService.editTopic(aTeachingPhaseId, topic);
        }

        if (body.getPhase() != null || body.getTimeFrame() != null) {
            Duration timeFrame = Optional.ofNullable(body.getTimeFrame()).map(UtilMapper::mapToDuration).orElse(null);

            teachingPhaseAppService.editTeachingPhase(aTeachingPhaseId, timeFrame, body.getPhase());
        }

        return ResponseEntity.ok().build();
    }
}
