package edu.kit.elst.core;

import edu.kit.elst.core.shared.UserId;

public class UserContext {
    private static final ThreadLocal<UserId> userId = new ThreadLocal<>();

    public static void setUserId(UserId id) {
        userId.set(id);
    }

    public static UserId getUserId() {
        return userId.get();
    }
}
