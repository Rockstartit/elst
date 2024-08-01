package edu.kit.elst.course_planning;

import edu.kit.elst.core.UserContext;
import edu.kit.elst.core.shared.UserId;
import edu.kit.elst.fixture.*;
import edu.kit.elst.rest_api.LearningCyclePhase;
import edu.kit.elst.rest_api.TeacherPresence;
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
public class CourseStructureTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LessonPlanningApiHelper lessonApiHelper;

    @Autowired
    private CoursePlanningApiHelper courseApiHelper;

    @Autowired
    private UserApiHelper userApiHelper;

    @Autowired
    private BuildingBlockApiHelper buildingBlockApiHelper;

    private final String KAI_USER_ID = "kaiUserId";

    @BeforeEach
    void setUp() {
        userApiHelper.userExists(KAI_USER_ID, "Kai", "Marquardt");

        UserContext.setUserId(new UserId(KAI_USER_ID));
    }

    @Test
    @SneakyThrows
    void can_get_course_structure() {
        String buildingBlockId = buildingBlockApiHelper.buildingBlockExists("Text", "Text building block description", "Some technology");

        String lessonId = lessonApiHelper.lessonExists("Lesson Topic");
        String teachingUnitId = lessonApiHelper.teachingUnitExists(lessonId, "Teaching Unit Topic");
        String teachingPhaseId = lessonApiHelper.teachingPhaseExists(teachingUnitId, "Teaching Phase Topic", TeacherPresence.ABSENT, 55, LearningCyclePhase.PRACTICE);
        String courseId = courseApiHelper.courseExists(lessonId, "Java");

        String pageId1 = courseApiHelper.pageExists(courseId, teachingPhaseId, "Startseite");
        String pageId2 = courseApiHelper.pageExists(courseId, teachingPhaseId, "Ende");

        mockMvc.perform(patch("/pages/{pageId}", pageId1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "notes": "Page implementation notes",
                                  "implementationStatus": "STARTED"
                                }
                                """))
                .andExpect(status().is2xxSuccessful());

        courseApiHelper.pageLinkExists(pageId1, pageId2, "Wenn Video angeschaut");
        String pageBuildingBlockId = courseApiHelper.pageBuildingBlockExists(pageId1, buildingBlockId);

        mockMvc.perform(get("/courses/{courseId}/structure", courseId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("""
                        [
                          {
                            "id": "%s",
                            "topic": "Teaching Unit Topic",
                            "teachingPhases": [
                              {
                                "id": "%s",
                                "topic": "Teaching Phase Topic",
                                "timeFrame": 55,
                                "phase": "PRACTICE",
                                "teacherPresence": "ABSENT",
                                "learningMaterials": [],
                                "pages": [
                                  {
                                    "id": "%s",
                                    "teachingPhaseId": "%s",
                                    "title": "Startseite",
                                    "notes": "Page implementation notes",
                                    "implementationStatus": "STARTED",
                                    "buildingBlocks": [
                                      {
                                        "pageBuildingBlockId": "%s",
                                        "buildingBlockId": "%s",
                                        "name": "Text",
                                        "description": "Text building block description",
                                        "releaseStatus": "IN_DEVELOPMENT",
                                        "order": 0
                                      }
                                    ],
                                    "linkedPages": [
                                      {
                                        "targetPageId": "%s",
                                        "targetPageTitle": "Ende",
                                        "condition": "Wenn Video angeschaut"
                                      }
                                    ],
                                    "mockups": [],
                                    "order": 0
                                  },
                                  {
                                    "id": "%s",
                                    "teachingPhaseId": "%s",
                                    "title": "Ende",
                                    "notes": null,
                                    "implementationStatus": "NOT_STARTED",
                                    "buildingBlocks": [],
                                    "linkedPages": [],
                                    "mockups": [],
                                    "order": 1
                                  }
                                ]
                              }
                            ]
                          }
                        ]
                        """.formatted(teachingUnitId, teachingPhaseId, pageId1, teachingPhaseId, pageBuildingBlockId, buildingBlockId, pageId2, pageId2, teachingPhaseId)));
    }
}
