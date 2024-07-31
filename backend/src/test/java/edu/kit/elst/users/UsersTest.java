package edu.kit.elst.users;

import edu.kit.elst.fixture.ELSTIntegrationTest;
import edu.kit.elst.fixture.UserApiHelper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ELSTIntegrationTest
public class UsersTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserApiHelper userApiHelper;

    @Test
    @SneakyThrows
    void can_update_user_profile() {
        String expectedUserId = "userId";
        String expectedFirstName = "Kai";
        String expectedLastName = "Happe";

        userApiHelper.userExists(expectedUserId, expectedFirstName, expectedLastName);

        mockMvc.perform(get("/users/{userId}", expectedUserId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("""
                    {
                      "id": "%s",
                      "firstName": "%s",
                      "lastName": "%s"
                    }
                """.formatted(expectedUserId, expectedFirstName, expectedLastName)));
    }
}
