package edu.kit.elst.rest_api;

import edu.kit.elst.users.User;

public class UserMapper {
    public static UserProfile mapToUserProfile(User user) {
        UserProfile dto = new UserProfile();

        dto.setId(user.id().value());

        user.firstName().ifPresent(dto::setFirstName);
        user.lastName().ifPresent(dto::setLastName);

        return dto;
    }
}
