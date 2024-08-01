package edu.kit.elst.lesson_planning;

import edu.kit.elst.fixture.ELSTIntegrationTest;
import edu.kit.elst.fixture.LessonApiHelper;
import edu.kit.elst.rest_api.LearningCyclePhase;
import edu.kit.elst.rest_api.TeacherPresence;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ELSTIntegrationTest
public class TeachingPhaseTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LessonApiHelper lessonApiHelper;

    @Test
    @SneakyThrows
    void can_create_teaching_phase() {
        String teachingPhaseTopic = "Einstieg";
        edu.kit.elst.rest_api.TeacherPresence teacherPresence = edu.kit.elst.rest_api.TeacherPresence.REMOTE;
        int timeFrame = 250;
        edu.kit.elst.rest_api.LearningCyclePhase phase = edu.kit.elst.rest_api.LearningCyclePhase.COLLABORATION;

        String lessonId = lessonApiHelper.lessonExists();
        String teachingUnitId = lessonApiHelper.teachingUnitExists(lessonId);
        String teachingPhaseId = lessonApiHelper.teachingPhaseExists(teachingUnitId, teachingPhaseTopic, teacherPresence, timeFrame, phase);

        mockMvc.perform(get("/teaching-units/{teachingUnitId}", teachingUnitId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("""
                        {
                          "teachingPhases": [
                            {
                                "id": "%s",
                                "topic": "%s",
                                "timeFrame": %d,
                                "phase": "%s",
                                "teacherPresence": "%s"
                            }
                          ]
                        }
                        """.formatted(teachingPhaseId, teachingPhaseTopic, timeFrame, phase, teacherPresence)));
    }

    @Test
    @SneakyThrows
    void can_edit_teaching_phase() {
        String lessonId = lessonApiHelper.lessonExists();
        String teachingUnitId = lessonApiHelper.teachingUnitExists(lessonId);
        String teachingPhaseId = lessonApiHelper.teachingPhaseExists(teachingUnitId);

        mockMvc.perform(patch("/teaching-phases/{teachingPhaseId}", teachingPhaseId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "topic": "Bienen Phase",
                                  "teacherPresence": "%s",
                                  "timeFrame": 15,
                                  "phase": "%s"
                                }
                                """.formatted(edu.kit.elst.rest_api.TeacherPresence.ABSENT, edu.kit.elst.rest_api.LearningCyclePhase.INQUIRY)))
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(get("/teaching-units/{teachingUnitId}", teachingUnitId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("""
                        {
                          "teachingPhases": [
                            {
                                "id": "%s",
                                "topic": "Bienen Phase",
                                "timeFrame": 15,
                                "phase": "%s",
                                "teacherPresence": "%s"
                            }
                          ]
                        }
                        """.formatted(teachingPhaseId, LearningCyclePhase.INQUIRY, TeacherPresence.ABSENT)));
    }

    @Test
    @SneakyThrows
    void can_reorder_teaching_phase() {
        String lessonId = lessonApiHelper.lessonExists();
        String teachingUnitId = lessonApiHelper.teachingUnitExists(lessonId);
        String teachingPhaseId1 = lessonApiHelper.teachingPhaseExists(teachingUnitId);
        String teachingPhaseId2 = lessonApiHelper.teachingPhaseExists(teachingUnitId);

        mockMvc.perform(post("/teaching-units/{teachingUnitId}/teaching-phases/order", teachingUnitId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                [
                                "%s",
                                "%s"
                                ]
                                """.formatted(teachingPhaseId2, teachingPhaseId1)))
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(get("/teaching-units/{teachingUnitId}", teachingUnitId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("""
                        {
                          "teachingPhases": [
                            {
                              "id": "%s",
                              "order": 0
                            },
                            {
                              "id": "%s",
                              "order": 1
                            }
                          ]
                        }
                        """.formatted(teachingPhaseId2, teachingPhaseId1)));
    }

    @Test
    @SneakyThrows
    void can_delete_teaching_phase() {
        String lessonId = lessonApiHelper.lessonExists();
        String teachingUnitId = lessonApiHelper.teachingUnitExists(lessonId);
        String teachingPhaseId = lessonApiHelper.teachingPhaseExists(teachingUnitId);

        mockMvc.perform(delete("/teaching-phases/{teachingPhaseId}", teachingPhaseId))
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(get("/teaching-units/{teachingUnitId}", teachingUnitId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("""
                        {
                          "teachingPhases": []
                        }
                        """));
    }
}
