package edu.kit.elst.core;

import org.springframework.util.StringUtils;

public class Guards {
    public static void notNull(Object obj, String name) {
        if (obj == null) {
            throw new IllegalArgumentException(String.format("%s cannot be null", name));
        }
    }

    public static void notEmptyBlankOrNull(String obj, String name) {
        if (StringUtils.hasText(obj)) {
            throw new IllegalArgumentException(String.format("%s cannot be empty, blank, or null", name));
        }
    }
}
