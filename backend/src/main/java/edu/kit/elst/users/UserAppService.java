package edu.kit.elst.users;

import edu.kit.elst.core.shared.UserId;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@AllArgsConstructor
public class UserAppService {
    private final Users users;

    public void editUserProfile(UserId userId, String firstName, String lastName) {
        User user = user(userId);

        user.firstName(firstName);
        user.lastName(lastName);

        users.save(user);
    }

    public Collection<User> users(Set<UserId> userIds) {
        return users.findAllById(userIds);
    }

    public User user(UserId userId) {
        return users.findById(userId).orElse(new User(userId));
    }
}
