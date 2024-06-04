package edu.kit.elst.rest_api;

import edu.kit.elst.core.shared.UserId;
import edu.kit.elst.users.User;
import edu.kit.elst.users.UserAppService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController implements UserApi {
    private final UserAppService userAppService;

    @Override
    public ResponseEntity<Void> editUserProfile(String userId, EditUserProfileRequest body) {
        UserId aUserId = new UserId(userId);

        userAppService.editUserProfile(aUserId, body.getFirstName(), body.getLastName());

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<UserProfile> getUserProfile(String userId) {
        UserId aUserId = new UserId(userId);

        User user = userAppService.user(aUserId);

        return ResponseEntity.ok(UserMapper.mapToUserProfile(user));
    }
}
