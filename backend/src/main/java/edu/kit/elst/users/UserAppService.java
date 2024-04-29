package edu.kit.elst.users;

import edu.kit.elst.core.shared.UserId;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Set;

@Service
@Transactional
@AllArgsConstructor
public class UserAppService {
    public Collection<User> users(Set<UserId> userIds) {
        return userIds.stream()
                .map(User::new)
                .toList();
    }

    public User user(UserId userId) {
        return new User(userId);
    }
}
