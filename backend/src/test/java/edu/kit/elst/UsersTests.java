package edu.kit.elst;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(DatabaseContainerConfiguration.class)
public class UsersTests {
    @Test
    void startup() {

    }
}
