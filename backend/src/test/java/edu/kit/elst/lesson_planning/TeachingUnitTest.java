package edu.kit.elst.lesson_planning;

import edu.kit.elst.fixture.ELSTIntegrationTest;
import edu.kit.elst.fixture.LessonPlanningApiHelper;
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
public class TeachingUnitTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LessonPlanningApiHelper lessonApiHelper;

    @Test
    @SneakyThrows
    void can_create_teaching_unit() {
        String teachingUnitTopic = "Motivation";

        String lessonId = lessonApiHelper.lessonExists();

        String teachingUnitId = lessonApiHelper.teachingUnitExists(lessonId, teachingUnitTopic);

        mockMvc.perform(get("/lessons/{lessonId}", lessonId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("""
                        {
                          "teachingUnits": [
                            {
                              "id": "%s",
                              "topic": "%s",
                              "order": 0
                            }
                          ]
                        }
                        """.formatted(teachingUnitId, teachingUnitTopic)));
    }

    @Test
    @SneakyThrows
    void can_edit_teaching_unit() {
        String lessonId = lessonApiHelper.lessonExists();
        String teachingUnitId = lessonApiHelper.teachingUnitExists(lessonId);

        mockMvc.perform(patch("/teaching-units/{teachingUnitId}", teachingUnitId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "topic": "Bienen Motivation",
                                  "curriculumAlignment": "Bildungsplan BW",
                                  "frameworkConditions": "Keine",
                                  "instructionMethods": "Praxis und Videos",
                                  "acquiredCompetences": "Umgang mit Bienen",
                                  "roughContentAnalysis": "Analyse",
                                  "didacticConsiderations": "Didaktische Überlegungen"
                                }
                                """))
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(get("/teaching-units/{teachingUnitId}", teachingUnitId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("""
                        {
                          "id": "%s",
                          "topic": "Bienen Motivation",
                          "curriculumAlignment": "Bildungsplan BW",
                          "frameworkConditions": "Keine",
                          "instructionMethods": "Praxis und Videos",
                          "acquiredCompetences": "Umgang mit Bienen",
                          "roughContentAnalysis": "Analyse",
                          "didacticConsiderations": "Didaktische Überlegungen",
                          "teachingPhases": []
                        }
                        """.formatted(teachingUnitId)));
    }

    @Test
    @SneakyThrows
    void can_reorder_teaching_units() {
        String lessonId = lessonApiHelper.lessonExists();
        String teachingUnitId1 = lessonApiHelper.teachingUnitExists(lessonId);
        String teachingUnitId2 = lessonApiHelper.teachingUnitExists(lessonId);

        mockMvc.perform(post("/lessons/{lessonId}/teaching-units/order", lessonId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                [
                                "%s",
                                "%s"
                                ]
                                """.formatted(teachingUnitId2, teachingUnitId1)))
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(get("/lessons/{lessonId}", lessonId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("""
                        {
                          "teachingUnits": [
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
                        """.formatted(teachingUnitId2, teachingUnitId1)));
    }

    @Test
    @SneakyThrows
    void can_delete_teaching_unit() {
        String lessonId = lessonApiHelper.lessonExists();
        String teachingUnitId = lessonApiHelper.teachingUnitExists(lessonId);

        mockMvc.perform(delete("/teaching-units/{teachingUnitId}", teachingUnitId))
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(get("/lessons/{lessonId}", lessonId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("""
                        {
                          "teachingUnits": []
                        }
                        """));
    }
}
