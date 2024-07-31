package edu.kit.elst.fixture;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AllArgsConstructor
public class UserApiHelper {
    private final MockMvc mockMvc;

    @SneakyThrows
    public void userExists(String userId, String firstName, String lastName) {
        mockMvc.perform(patch("/users/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "firstName": "%s",
                        "lastName": "%s"
                    }
                """.formatted(firstName, lastName))).andExpect(status().is2xxSuccessful());
    }
}
