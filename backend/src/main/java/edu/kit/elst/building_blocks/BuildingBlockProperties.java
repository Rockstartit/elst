package edu.kit.elst.building_blocks;

import edu.kit.elst.core.shared.BuildingBlockPropertyId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingBlockProperties extends JpaRepository<BuildingBlockProperty, BuildingBlockPropertyId> {
    @Query("select max(property.order) from BuildingBlockProperty property " +
            "where property.buildingBlock = :buildingBlock " +
            "group by property.buildingBlock.id ")
    Long maxOrderByBuildingBlock(BuildingBlock buildingBlock);
}
