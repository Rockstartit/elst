package edu.kit.elst.fixture;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;

@TestConfiguration
public class ApiHelpers {
    @Bean
    public BuildingBlockApiHelper buildingBlockApiHelper(MockMvc mockMvc) {
        return new BuildingBlockApiHelper(mockMvc);
    }

    @Bean
    public UserApiHelper userApiHelper(MockMvc mockMvc) {
        return new UserApiHelper(mockMvc);
    }
}
