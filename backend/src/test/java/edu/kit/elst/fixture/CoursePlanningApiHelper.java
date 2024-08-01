package edu.kit.elst.fixture;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AllArgsConstructor
public class CoursePlanningApiHelper {
    private final MockMvc mockMvc;

    @SneakyThrows
    public String courseExists(String lessonId) {
        return courseExists(lessonId, "Wordpress");
    }

    @SneakyThrows
    public String courseExists(String lessonId, String technologyWish) {
        MvcResult result = mockMvc.perform(post("/lessons/{lessonId}/courses", lessonId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "technologyWish": "%s"
                                }
                                """.formatted(technologyWish)))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        return result.getResponse().getContentAsString().replace("\"", "");
    }

    @SneakyThrows
    public String pageExists(String courseId, String teachingPhaseId) {
        return pageExists(courseId, teachingPhaseId, "Startseite");
    }

    @SneakyThrows
    public String pageExists(String courseId, String teachingPhaseId, String title) {
        MvcResult result = mockMvc.perform(post("/courses/{courseId}/pages", courseId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "title": "%s",
                                  "teachingPhaseId": "%s"
                                }
                                """.formatted(title, teachingPhaseId)))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        return result.getResponse().getContentAsString().replace("\"", "");
    }

    @SneakyThrows
    public void pageLinkExists(String pageId, String targetPageId, String condition) {
        mockMvc.perform(post("/page-links")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "pageId": "%s",
                                  "targetPageId": "%s",
                                  "condition": "%s"
                                }
                                """.formatted(pageId, targetPageId, condition)))
                .andExpect(status().is2xxSuccessful());

    }

    public void pageLinkExists(String pageId, String targetPageId) {
        pageLinkExists(pageId, targetPageId, "Mehr als 10 Punkte erreicht");
    }

    @SneakyThrows
    public String pageBuildingBlockExists(String pageId, String buildingBlockId) {
        MvcResult result = mockMvc.perform(post("/pages/{pageId}/page-building-blocks", pageId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "buildingBlockId": "%s"
                                }
                                """.formatted(buildingBlockId)))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        return result.getResponse().getContentAsString().replace("\"", "");
    }
}
