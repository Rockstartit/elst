package edu.kit.elst.course_planning;

import edu.kit.elst.core.UserContext;
import edu.kit.elst.core.shared.UserId;
import edu.kit.elst.fixture.CoursePlanningApiHelper;
import edu.kit.elst.fixture.ELSTIntegrationTest;
import edu.kit.elst.fixture.LessonPlanningApiHelper;
import edu.kit.elst.fixture.UserApiHelper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ELSTIntegrationTest
public class CourseTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LessonPlanningApiHelper lessonApiHelper;

    @Autowired
    private CoursePlanningApiHelper courseApiHelper;

    @Autowired
    private UserApiHelper userApiHelper;

    private final String KAI_USER_ID = "kaiUserId";

    @BeforeEach
    void setUp() {
        userApiHelper.userExists(KAI_USER_ID, "Kai", "Marquardt");

        UserContext.setUserId(new UserId(KAI_USER_ID));
    }

    @Test
    @SneakyThrows
    void can_create_course_based_on_lesson() {
        String technologyWish = "Java";

        String lessonId = lessonApiHelper.lessonExists();

        String courseId = courseApiHelper.courseExists(lessonId, technologyWish);

        mockMvc.perform(get("/courses"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("""
                                [
                                  {
                                    "id": "%s",
                                    "lessonId": "%s",
                                    "technologyWish": "%s"
                                  }
                                ]
                        """.formatted(courseId, lessonId, technologyWish)));
    }

    @Test
    @SneakyThrows
    void can_edit_course() {
        String technologyWish = "Joomla";
        String notes = "course implementation notes";

        String lessonId = lessonApiHelper.lessonExists();
        String courseId = courseApiHelper.courseExists(lessonId);

        mockMvc.perform(patch("/courses/{courseId}", courseId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "technologyWish": "%s",
                                  "notes": "%s"
                                }
                                """.formatted(technologyWish, notes)))
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(get("/courses/{courseId}", courseId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("""
                        {
                          "id": "%s",
                          "lessonId": "%s",
                          "technologyWish": "%s",
                          "notes": "%s"
                        }
                        """.formatted(courseId, lessonId, technologyWish, notes)));
    }

    @Test
    @SneakyThrows
    void can_delete_course() {
        String lessonId = lessonApiHelper.lessonExists();
        String courseId = courseApiHelper.courseExists(lessonId);

        mockMvc.perform(delete("/courses/{courseId}", courseId))
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(MockMvcRequestBuilders.get("/courses", courseId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("[]"));
    }
}
