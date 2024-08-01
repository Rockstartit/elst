package edu.kit.elst.course_planning;

import edu.kit.elst.core.UserContext;
import edu.kit.elst.core.shared.UserId;
import edu.kit.elst.fixture.*;
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
public class PageBuildingBlockTest {
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
    void can_add_page_building_block() {
        String buildingBlockName = "Text";
        String buildingBlockDescription = "Description of a Text";

        String buildingBlockId = buildingBlockApiHelper.buildingBlockExists(buildingBlockName, buildingBlockDescription, "Some technology");

        String lessonId = lessonApiHelper.lessonExists();
        String teachingUnitId = lessonApiHelper.teachingUnitExists(lessonId);
        String teachingPhaseId = lessonApiHelper.teachingPhaseExists(teachingUnitId);
        String courseId = courseApiHelper.courseExists(lessonId);

        String pageId = courseApiHelper.pageExists(courseId, teachingPhaseId);

        String pageBuildingBlockId = courseApiHelper.pageBuildingBlockExists(pageId, buildingBlockId);

        mockMvc.perform(get("/pages/{pageId}", pageId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("""
                        {
                          "buildingBlocks": [
                            {
                              "pageBuildingBlockId": "%s",
                              "buildingBlockId": "%s",
                              "name": "%s",
                              "description": "%s",
                              "releaseStatus": "IN_DEVELOPMENT",
                              "order": 0
                            }
                          ]
                        }
                        """.formatted(pageBuildingBlockId, buildingBlockId, buildingBlockName, buildingBlockDescription)));
    }

    @Test
    @SneakyThrows
    void can_reorder_page_building_blocks() {
        String buildingBlockId = buildingBlockApiHelper.buildingBlockExists();

        String lessonId = lessonApiHelper.lessonExists();
        String teachingUnitId = lessonApiHelper.teachingUnitExists(lessonId);
        String teachingPhaseId = lessonApiHelper.teachingPhaseExists(teachingUnitId);
        String courseId = courseApiHelper.courseExists(lessonId);

        String pageId = courseApiHelper.pageExists(courseId, teachingPhaseId);

        String pageBuildingBlockId1 = courseApiHelper.pageBuildingBlockExists(pageId, buildingBlockId);
        String pageBuildingBlockId2 = courseApiHelper.pageBuildingBlockExists(pageId, buildingBlockId);

        mockMvc.perform(post("/pages/{pageId}/page-building-blocks/order", pageId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        [
                          "%s",
                          "%s"
                        ]
                        """.formatted(pageBuildingBlockId2, pageBuildingBlockId1)))
                        .andExpect(status().is2xxSuccessful());

        mockMvc.perform(get("/pages/{pageId}", pageId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("""
                        {
                          "buildingBlocks": [
                            {
                              "pageBuildingBlockId": "%s",
                              "order": 0
                            },
                            {
                              "pageBuildingBlockId": "%s",
                              "order": 1
                            }
                          ]
                        }
                        """.formatted(pageBuildingBlockId2, pageBuildingBlockId1)));
    }

    @Test
    @SneakyThrows
    void can_remove_page_building_blocks() {
        String buildingBlockId = buildingBlockApiHelper.buildingBlockExists();

        String lessonId = lessonApiHelper.lessonExists();
        String teachingUnitId = lessonApiHelper.teachingUnitExists(lessonId);
        String teachingPhaseId = lessonApiHelper.teachingPhaseExists(teachingUnitId);
        String courseId = courseApiHelper.courseExists(lessonId);

        String pageId = courseApiHelper.pageExists(courseId, teachingPhaseId);

        String pageBuildingBlockId = courseApiHelper.pageBuildingBlockExists(pageId, buildingBlockId);

        mockMvc.perform(delete("/page-building-blocks/{pageBuildingBlockId}", pageBuildingBlockId))
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(get("/pages/{pageId}", pageId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("""
                        {
                          "buildingBlocks": []
                        }
                        """));
    }

    @Test
    @SneakyThrows
    void can_configure_page_building_blocks() {
        String propertyKey = "some_key";
        String propertyType = "STRING";
        String propertyName = "Value";
        String propertyDescription = "String value for some property";
        String propertyValue = "the expected value of the property";

        String buildingBlockId = buildingBlockApiHelper.buildingBlockExists();

        buildingBlockApiHelper.buildingBlockPropertyExists(buildingBlockId, propertyKey, propertyType, propertyName, propertyDescription);

        String lessonId = lessonApiHelper.lessonExists();
        String teachingUnitId = lessonApiHelper.teachingUnitExists(lessonId);
        String teachingPhaseId = lessonApiHelper.teachingPhaseExists(teachingUnitId);
        String courseId = courseApiHelper.courseExists(lessonId);

        String pageId = courseApiHelper.pageExists(courseId, teachingPhaseId);

        String pageBuildingBlockId = courseApiHelper.pageBuildingBlockExists(pageId, buildingBlockId);

        mockMvc.perform(put("/page-building-blocks/{pageBuildingBlockId}/properties/{key}", pageBuildingBlockId, propertyKey)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "value": "%s"
                                }
                                """.formatted(propertyValue)))
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(get("/page-building-blocks/{pageBuildingBlockId}/properties", pageBuildingBlockId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("""
                        [
                          {
                            "key": "%s",
                            "type": "%s",
                            "displayName": "%s",
                            "description": "%s",
                            "order": 0,
                            "value": "%s"
                          }
                        ]
                        """.formatted(propertyKey, propertyType, propertyName, propertyDescription, propertyValue)));
    }
}
