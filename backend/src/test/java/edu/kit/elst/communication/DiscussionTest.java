package edu.kit.elst.communication;

import edu.kit.elst.core.UserContext;
import edu.kit.elst.core.shared.UserId;
import edu.kit.elst.fixture.ELSTIntegrationTest;
import edu.kit.elst.fixture.UserApiHelper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ELSTIntegrationTest
public class DiscussionTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserApiHelper userApiHelper;

    private final String KAI_USER_ID = "kaiUserId";
    private final String LUCIA_USER_ID = "luciaUserId";

    @BeforeEach
    void setUp() {
        userApiHelper.userExists(KAI_USER_ID, "Kai", "Marquardt");
        userApiHelper.userExists(LUCIA_USER_ID, "Lucia", "Happe");
    }

    @Test
    @SneakyThrows
    void can_start_discussion() {
        String title = "Title Example";
        String initialComment = "My Comment";

        switchUserContext(KAI_USER_ID);

        String discussionId = startDiscussion(title, initialComment);

        mockMvc.perform(get("/discussions/{discussionId}", discussionId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("""
                             {
                               "id": "%s",
                               "title": "%s",
                               "createdBy": {
                                 "id": "%s"
                               },
                               "state": "OPEN",
                               "references": []
                             }
                        """.formatted(discussionId, title, KAI_USER_ID)));

        mockMvc.perform(get("/discussions/{discussionId}/comments", discussionId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("""
                             [
                               {
                                 "content": "%s",
                                 "createdBy": {
                                   "id": "%s"
                                 }
                               }
                             ]
                        """.formatted(initialComment, KAI_USER_ID)));
    }

    @Test
    @SneakyThrows
    void can_comment_on_discussion() {
        String content = "Lucias comment";

        switchUserContext(KAI_USER_ID);

        String discussionId = startDiscussion();

        switchUserContext(LUCIA_USER_ID);

        mockMvc.perform(post("/discussions/{discussionId}/comments", discussionId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                            {
                              "content": "%s"
                            }
                        """.formatted(content)))
                        .andExpect(status().is2xxSuccessful());

        mockMvc.perform(get("/discussions/{discussionId}/comments", discussionId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("""
                             [
                               {
                                 "createdBy": {
                                   "id": "%s"
                                 }
                               },
                               {
                                 "content": "%s",
                                 "createdBy": {
                                   "id": "%s"
                                 }
                               }
                             ]
                        """.formatted(KAI_USER_ID, content, LUCIA_USER_ID)));
    }

    @Test
    @SneakyThrows
    void can_resolve_discussion() {
        switchUserContext(KAI_USER_ID);

        String discussionId = startDiscussion();

        mockMvc.perform(post("/discussions/{discussionId}", discussionId))
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(get("/discussions/{discussionId}", discussionId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("""
                             {
                               "id": "%s",
                               "state": "RESOLVED"
                             }
                        """.formatted(discussionId)));
    }

    @SneakyThrows
    private String startDiscussion() {
        return startDiscussion("Title", "Test");
    }

    @SneakyThrows
    private String startDiscussion(String title, String initialComment) {
        MvcResult result = mockMvc.perform(post("/discussions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "title": "%s",
                          "comment": "%s"
                        }
                        """.formatted(title, initialComment)))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        return result.getResponse().getContentAsString().replaceAll("\"", "");
    }

    private void switchUserContext(String userId) {
        UserContext.setUserId(new UserId(userId));
    }
}
