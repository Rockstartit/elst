package edu.kit.elst;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Import(DatabaseContainerConfiguration.class)
public class UsersTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    void can_update_user_profile() {
        String expectedUserId = "userId";

        mockMvc.perform(patch("/users/{userId}", expectedUserId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "firstName": "Kai",
                        "lastName": "Happe"
                    }
                """)).andExpect(status().is2xxSuccessful());

        mockMvc.perform(get("/users/{userId}", expectedUserId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("""
                    {
                      "id": "%s",
                      "firstName": "Kai",
                      "lastName": "Happe"
                    }
                """.formatted(expectedUserId)));
    }
}
