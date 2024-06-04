package edu.kit.elst.users;

import edu.kit.elst.core.shared.UserId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Entity
@Getter
@Setter
@Table(name = "users")
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class User {
    @EmbeddedId
    private final UserId id;

    private String firstName;
    private String lastName;

    User(UserId id) {
        this.id = id;
    }

    public Optional<String> firstName() {
        return Optional.ofNullable(firstName);
    }

    public Optional<String> lastName() {
        return Optional.ofNullable(lastName);
    }
}
