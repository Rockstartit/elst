package edu.kit.elst.course_conceptualization;

import edu.kit.elst.core.shared.PageId;

public class PageNotFoundException extends RuntimeException {
    public PageNotFoundException(PageId pageId) {
        super(String.format("page %s does not exist", pageId));
    }
}
