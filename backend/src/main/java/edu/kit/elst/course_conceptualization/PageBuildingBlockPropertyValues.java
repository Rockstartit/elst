package edu.kit.elst.course_conceptualization;

import edu.kit.elst.core.shared.PageBuildingBlockId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.UUID;

@Repository
public interface PageBuildingBlockPropertyValues extends JpaRepository<PageBuildingBlockPropertyValue, PageBuildingBlockPropertyId> {
    @Query("select propertyValue from PageBuildingBlockPropertyValue propertyValue " +
            "where propertyValue.propertyId.pageBuildingBlockId = :pageBuildingBlockId")
    Collection<PageBuildingBlockPropertyValue> findAllByPageBuildingBlockId(UUID pageBuildingBlockId);

    default Collection<PageBuildingBlockPropertyValue> findAllByPageBuildingBlockId(PageBuildingBlockId pageBuildingBlockId) {
        return findAllByPageBuildingBlockId(pageBuildingBlockId.value());
    }
}
