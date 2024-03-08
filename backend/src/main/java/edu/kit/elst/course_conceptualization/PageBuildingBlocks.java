package edu.kit.elst.course_conceptualization;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PageBuildingBlocks extends CrudRepository<PageBuildingBlock, PageBuildingBlockId> {
    static PageBuildingBlockId nextIdentity() {
        return new PageBuildingBlockId(UUID.randomUUID());
    }
}
