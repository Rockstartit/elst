package edu.kit.elst.users;

import edu.kit.elst.core.shared.UserId;
import lombok.Getter;

@Getter
public class User {
    private final UserId id;
    private String firstName;
    private String lastName;

    User(UserId id) {
        this.id = id;
    }
}
