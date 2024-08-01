package edu.kit.elst.lesson_planning;

import edu.kit.elst.fixture.ELSTIntegrationTest;
import edu.kit.elst.fixture.LessonPlanningApiHelper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ELSTIntegrationTest
public class LessonTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LessonPlanningApiHelper lessonApiHelper;

    @Test
    @SneakyThrows
    void can_create_lesson() {
        String topic = "Bienen";

        String lessonId = lessonApiHelper.lessonExists(topic);

        mockMvc.perform(get("/lessons"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("""
                        [
                          {
                            "id": "%s",
                            "topic": "%s"
                          }
                        ]
                        """.formatted(lessonId, topic)));
    }

    @Test
    @SneakyThrows
    void can_edit_lesson() {
        String lessonId = lessonApiHelper.lessonExists();

        mockMvc.perform(patch("/lessons/{lessonId}", lessonId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "topic": "Bienen Kurs",
                                  "instructionalParameters": {
                                    "subject": "Biologie",
                                    "targetAudience": "8. Klasse",
                                    "schedule": "jedes Jahr"
                                  },
                                  "preparationFactors": {
                                    "learningPrerequisites": "Keine Bienenallergie",
                                    "priorKnowledge": "Imkerkurs",
                                    "thematicAreas": "Natur"
                                  },
                                  "license": "Frei zugänglich"
                                }
                                """))
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(get("/lessons/{lessonId}", lessonId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("""
                        {
                          "id": "%s",
                          "topic": "Bienen Kurs",
                          "subject": "Biologie",
                          "targetAudience": "8. Klasse",
                          "schedule": "jedes Jahr",
                          "priorKnowledge": "Imkerkurs",
                          "learningPrerequisites": "Keine Bienenallergie",
                          "thematicAreas": "Natur",
                          "license": "Frei zugänglich",
                          "teachingUnits": []
                        }
                        """.formatted(lessonId)));
    }

    @Test
    @SneakyThrows
    void can_delete_lesson() {
        String lessonId = lessonApiHelper.lessonExists();

        mockMvc.perform(delete("/lessons/{lessonId}", lessonId))
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(get("/lessons"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("[]"));
    }
}
