package edu.kit.elst.building_blocks;

import edu.kit.elst.fixture.BuildingBlockApiHelper;
import edu.kit.elst.fixture.ELSTIntegrationTest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ELSTIntegrationTest
public class BuildingBlockTest {
    @Autowired
    private BuildingBlockApiHelper buildingBlockApiHelper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    void can_browse_building_blocks() {
        String name = "Text";
        String description = "Eine Komponente zum Darstellen eines Textes.";
        String technology = "Vue";

        String buildingBlockId = buildingBlockApiHelper.buildingBlockExists(name, description, technology);

        mockMvc.perform(get("/building-blocks"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("""
                                [
                                  {
                                    "id": "%s",
                                    "name": "%s",
                                    "description": "%s",
                                    "technology": "%s",
                                    "properties": [],
                                    "releaseStatus": "IN_DEVELOPMENT"
                                  }
                                ]
                        """.formatted(buildingBlockId, name, description, technology)));
    }

    @Test
    @SneakyThrows
    void can_release_building_blocks() {
        String buildingBlockId = buildingBlockApiHelper.buildingBlockExists();

        mockMvc.perform(post("/building-blocks/{buildingBlockId}/release", buildingBlockId));

        mockMvc.perform(get("/building-blocks"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("""
                                [
                                  {
                                    "id": "%s",
                                    "releaseStatus": "RELEASED"
                                  }
                                ]
                        """.formatted(buildingBlockId)));
    }

    @Test
    @SneakyThrows
    void can_edit_building_block() {
        String name = "editedText";
        String description = "edited Eine Komponente zum Darstellen eines Textes.";
        String technology = "edited Vue";

        String buildingBlockId = buildingBlockApiHelper.buildingBlockExists();

        mockMvc.perform(patch("/building-blocks/{buildingBlockId}", buildingBlockId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "%s",
                                  "technology": "%s",
                                  "description": "%s"
                                }
                                """.formatted(name, technology, description)))
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(get("/building-blocks"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("""
                                [
                                  {
                                    "id": "%s",
                                    "name": "%s",
                                    "description": "%s",
                                    "technology": "%s"
                                  }
                                ]
                        """.formatted(buildingBlockId, name, description, technology)));
    }

    @Test
    @SneakyThrows
    void can_edit_readme() {
        String content = "readme";

        String buildingBlockId = buildingBlockApiHelper.buildingBlockExists();

        mockMvc.perform(put("/building-blocks/{buildingBlockId}/readme", buildingBlockId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "content": "%s"
                                }
                                """.formatted(content)))
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(get("/building-blocks/{buildingBlockId}/readme", buildingBlockId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string("%s".formatted(content)));
    }

    @Test
    @SneakyThrows
    void can_create_properties() {
        String key = "value_1";
        String type = "STRING";
        String displayName = "Value 1";
        String description = "First Value";

        String buildingBlockId = buildingBlockApiHelper.buildingBlockExists();

        buildingBlockApiHelper.buildingBlockPropertyExists(buildingBlockId, key, type, displayName, description);

        mockMvc.perform(get("/building-blocks"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("""
                                [
                                  {
                                    "id": "%s",
                                    "properties": [
                                        {
                                          "key": "%s",
                                          "type": "%s",
                                          "displayName": "%s",
                                          "description": "%s"
                                        }
                                    ]
                                  }
                                ]
                        """.formatted(buildingBlockId, key, type, displayName, description)));
    }

    @Test
    @SneakyThrows
    void can_edit_properties() {
        String key = "value_1";
        String type = "STRING";
        String displayName = "edited Value 1";
        String description = "edited First Value";

        String buildingBlockId = buildingBlockApiHelper.buildingBlockExists();

        buildingBlockApiHelper.buildingBlockPropertyExists(buildingBlockId, key);

        mockMvc.perform(patch("/building-blocks/{buildingBlockId}/properties/{key}", buildingBlockId, key)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "type": "%s",
                          "displayName": "%s",
                          "description": "%s"
                        }
                        """.formatted(type, displayName, description)))
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(get("/building-blocks"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("""
                                [
                                  {
                                    "id": "%s",
                                    "properties": [
                                        {
                                          "key": "%s",
                                          "type": "%s",
                                          "displayName": "%s",
                                          "description": "%s"
                                        }
                                    ]
                                  }
                                ]
                        """.formatted(buildingBlockId, key, type, displayName, description)));
    }

    @Test
    @SneakyThrows
    void can_delete_properties() {
        String key = "key";

        String buildingBlockId = buildingBlockApiHelper.buildingBlockExists();

        buildingBlockApiHelper.buildingBlockPropertyExists(buildingBlockId, key);

        mockMvc.perform(delete("/building-blocks/{buildingBlockId}/properties/{key}", buildingBlockId, key)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(get("/building-blocks"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("""
                                [
                                  {
                                    "id": "%s",
                                    "properties": []
                                  }
                                ]
                        """.formatted(buildingBlockId, key)));
    }
}
