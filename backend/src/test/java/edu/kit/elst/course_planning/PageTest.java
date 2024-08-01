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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ELSTIntegrationTest
public class PageTest {
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
    void can_create_page() {
        String pageTitle = "Startseite";

        String lessonId = lessonApiHelper.lessonExists();
        String teachingUnitId = lessonApiHelper.teachingUnitExists(lessonId);
        String teachingPhaseId = lessonApiHelper.teachingPhaseExists(teachingUnitId);
        String courseId = courseApiHelper.courseExists(lessonId);

        String pageId = courseApiHelper.pageExists(courseId, teachingPhaseId, pageTitle);

        mockMvc.perform(get("/pages/{pageId}", pageId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("""
                        {
                          "id": "%s",
                          "teachingPhaseId": "%s",
                          "title": "%s",
                          "implementationStatus": "NOT_STARTED",
                          "buildingBlocks": [],
                          "linkedPages": [],
                          "mockups": [],
                          "order": 0
                        }
                        """.formatted(pageId, teachingPhaseId, pageTitle)));
    }

    @Test
    @SneakyThrows
    void can_edit_page() {
        String pageTitle = "New Startseite";
        String pageNotes = "page implementation notes";
        ImplementationStatus implementationStatus = ImplementationStatus.WAITING_FOR_FEEDBACK;

        String lessonId = lessonApiHelper.lessonExists();
        String teachingUnitId = lessonApiHelper.teachingUnitExists(lessonId);
        String teachingPhaseId = lessonApiHelper.teachingPhaseExists(teachingUnitId);
        String courseId = courseApiHelper.courseExists(lessonId);
        String pageId = courseApiHelper.pageExists(courseId, teachingPhaseId);

        mockMvc.perform(patch("/pages/{pageId}", pageId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "title": "%s",
                                  "notes": "%s",
                                  "implementationStatus": "%s"
                                }
                                """.formatted(pageTitle, pageNotes, implementationStatus)))
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(get("/pages/{pageId}", pageId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("""
                        {
                          "id": "%s",
                          "teachingPhaseId": "%s",
                          "title": "%s",
                          "notes": "%s",
                          "implementationStatus": "%s",
                          "buildingBlocks": [],
                          "linkedPages": [],
                          "mockups": [],
                          "order": 0
                        }
                        """.formatted(pageId, teachingPhaseId, pageTitle, pageNotes, implementationStatus)));
    }

    @Test
    @SneakyThrows
    void can_delete_page() {
        String lessonId = lessonApiHelper.lessonExists();
        String teachingUnitId = lessonApiHelper.teachingUnitExists(lessonId);
        String teachingPhaseId = lessonApiHelper.teachingPhaseExists(teachingUnitId);
        String courseId = courseApiHelper.courseExists(lessonId);
        String pageId = courseApiHelper.pageExists(courseId, teachingPhaseId);

        mockMvc.perform(delete("/pages/{pageId}", pageId))
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(get("/pages/{pageId}", pageId))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    void can_link_two_pages() {
        String condition = "Wenn mehr als 10 Punkte erreicht wurden";
        String targetPageTitle = "Zielseite";

        String lessonId = lessonApiHelper.lessonExists();
        String teachingUnitId = lessonApiHelper.teachingUnitExists(lessonId);
        String teachingPhaseId = lessonApiHelper.teachingPhaseExists(teachingUnitId);
        String courseId = courseApiHelper.courseExists(lessonId);
        String pageId1 = courseApiHelper.pageExists(courseId, teachingPhaseId);
        String pageId2 = courseApiHelper.pageExists(courseId, teachingPhaseId, targetPageTitle);

        courseApiHelper.pageLinkExists(pageId1, pageId2, condition);

        mockMvc.perform(get("/pages/{pageId}", pageId1))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("""
                        {
                          "linkedPages": [
                            {
                              "targetPageId": "%s",
                              "targetPageTitle": "%s",
                              "condition": "%s"
                            }
                          ]
                        }
                        """.formatted(pageId2, targetPageTitle, condition)));
    }

    @Test
    @SneakyThrows
    void can_edit_page_link_condition() {
        String condition = "Wenn mehr als 50% erreicht wurden";

        String lessonId = lessonApiHelper.lessonExists();
        String teachingUnitId = lessonApiHelper.teachingUnitExists(lessonId);
        String teachingPhaseId = lessonApiHelper.teachingPhaseExists(teachingUnitId);
        String courseId = courseApiHelper.courseExists(lessonId);
        String pageId1 = courseApiHelper.pageExists(courseId, teachingPhaseId);
        String pageId2 = courseApiHelper.pageExists(courseId, teachingPhaseId);

        courseApiHelper.pageLinkExists(pageId1, pageId2);

        mockMvc.perform(patch("/page-links/{pageId}/to/{targetPageId}", pageId1, pageId2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "condition": "%s"
                                }
                                """.formatted(condition)))
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(get("/pages/{pageId}", pageId1))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("""
                        {
                          "linkedPages": [
                            {
                              "targetPageId": "%s",
                              "condition": "%s"
                            }
                          ]
                        }
                        """.formatted(pageId2, condition)));
    }

    @Test
    @SneakyThrows
    void can_delete_page_link() {
        String lessonId = lessonApiHelper.lessonExists();
        String teachingUnitId = lessonApiHelper.teachingUnitExists(lessonId);
        String teachingPhaseId = lessonApiHelper.teachingPhaseExists(teachingUnitId);
        String courseId = courseApiHelper.courseExists(lessonId);
        String pageId1 = courseApiHelper.pageExists(courseId, teachingPhaseId);
        String pageId2 = courseApiHelper.pageExists(courseId, teachingPhaseId);

        courseApiHelper.pageLinkExists(pageId1, pageId2);

        mockMvc.perform(delete("/page-links/{pageId}/to/{targetPageId}", pageId1, pageId2))
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(get("/pages/{pageId}", pageId1))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("""
                        {
                          "linkedPages": []
                        }
                        """));
    }

    @Test
    @SneakyThrows
    void can_reorder_pages() {
        String lessonId = lessonApiHelper.lessonExists();
        String teachingUnitId = lessonApiHelper.teachingUnitExists(lessonId);
        String teachingPhaseId = lessonApiHelper.teachingPhaseExists(teachingUnitId);
        String courseId = courseApiHelper.courseExists(lessonId);
        String pageId1 = courseApiHelper.pageExists(courseId, teachingPhaseId);
        String pageId2 = courseApiHelper.pageExists(courseId, teachingPhaseId);

        mockMvc.perform(post("/courses/{courseId}/pages/order", courseId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                [
                                  "%s",
                                  "%s"
                                ]
                                """.formatted(pageId2, pageId1)))
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(get("/courses/{courseId}/structure", courseId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("""
                [
                  {
                    "teachingPhases": [
                      {
                        "pages": [
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
                    ]
                  }
                ]
                """.formatted(pageId2, pageId1)));
    }
}
