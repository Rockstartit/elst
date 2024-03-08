package edu.kit.elst.course_conceptualization;

public class PageNotFoundException extends RuntimeException {
    public PageNotFoundException(PageId pageId) {
        super(String.format("page %s does not exist", pageId));
    }
}
