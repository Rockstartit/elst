package edu.kit.elst.building_blocks;

import edu.kit.elst.core.shared.BuildingBlockId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReadMeRepository extends JpaRepository<ReadMe, BuildingBlockId> {
}
