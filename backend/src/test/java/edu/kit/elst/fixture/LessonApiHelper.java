package edu.kit.elst.fixture;

import edu.kit.elst.rest_api.LearningCyclePhase;
import edu.kit.elst.rest_api.TeacherPresence;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AllArgsConstructor
public class LessonApiHelper {
    private MockMvc mockMvc;

    @SneakyThrows
    public String lessonExists() {
        return lessonExists("Bienen");
    }

    @SneakyThrows
    public String lessonExists(String topic) {
        MvcResult result = mockMvc.perform(post("/lessons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "topic": "%s"
                                }
                                """.formatted(topic)))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        return result.getResponse().getContentAsString().replace("\"", "");
    }

    @SneakyThrows
    public String teachingUnitExists(String lessonId) {
        return teachingUnitExists(lessonId, "Motivation");
    }

    @SneakyThrows
    public String teachingUnitExists(String lessonId, String topic) {
        MvcResult result = mockMvc.perform(post("/lessons/{lessonId}/teaching-units", lessonId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "topic": "%s"
                                }
                                """.formatted(topic)))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        return result.getResponse().getContentAsString().replace("\"", "");
    }

    @SneakyThrows
    public String teachingPhaseExists(String teachingUnitId) {
        return teachingPhaseExists(teachingUnitId, "Einstieg", TeacherPresence.ON_SITE, 240, LearningCyclePhase.DISCUSSION);
    }

    @SneakyThrows
    public String teachingPhaseExists(String teachingUnitId, String topic, TeacherPresence teacherPresence, int timeFrame, LearningCyclePhase phase) {
        MvcResult result = mockMvc.perform(post("/teaching-units/{teachingUnitId}/teaching-phases", teachingUnitId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                    {
                                      "topic": "%s",
                                      "teacherPresence": "%s",
                                      "timeFrame": %d,
                                      "phase": "%s"
                                    }
                                """.formatted(topic, teacherPresence, timeFrame, phase)))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        return result.getResponse().getContentAsString().replace("\"", "");
    }
}
