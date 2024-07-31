package edu.kit.elst.fixture;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AllArgsConstructor
public class BuildingBlockApiHelper {
    private MockMvc mockMvc;

    @SneakyThrows
    public String buildingBlockExists() {
        return buildingBlockExists("Text", "Eine Komponente zum Darstellen eines Textes.", "Vue");
    }

    @SneakyThrows
    public String buildingBlockExists(String name, String description, String technology) {
        MvcResult result = mockMvc.perform(post("/building-blocks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "%s",
                                  "technology": "%s",
                                  "description": "%s"
                                }
                                """.formatted(name, technology, description)))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        return result.getResponse().getContentAsString().replaceAll("\"", "");
    }

    @SneakyThrows
    public void buildingBlockPropertyExists(String buildingBlockId, String key) {
        buildingBlockPropertyExists(buildingBlockId, key, "STRING", "Value 1", "First value");
    }

    @SneakyThrows
    public void buildingBlockPropertyExists(String buildingBlockId, String key, String type, String displayName, String description) {
        mockMvc.perform(post("/building-blocks/{buildingBlockId}/properties", buildingBlockId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "key": "%s",
                          "type": "%s",
                          "displayName": "%s",
                          "description": "%s"
                        }
                        """.formatted(key, type, displayName, description)))
                .andExpect(status().is2xxSuccessful());
    }
}
