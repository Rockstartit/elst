package edu.kit.elst.building_blocks;

import edu.kit.elst.content_upload.StorageService;
import edu.kit.elst.core.UserContext;
import edu.kit.elst.core.shared.BuildingBlockId;
import edu.kit.elst.core.shared.FileId;
import edu.kit.elst.core.shared.MockupId;
import edu.kit.elst.core.shared.MockupNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@AllArgsConstructor
public class BuildingBlockMockupAppService {
    private final StorageService storageService;
    private final BuildingBlockMockups buildingBlockMockups;

    public MockupId uploadMockup(BuildingBlockId buildingBlockId, MultipartFile file, String description) {
        FileId fileId = storageService.storeFile(file);

        BuildingBlockMockup mockup = new BuildingBlockMockup(buildingBlockId, fileId, UserContext.getUserId());
        mockup.description(description);

        buildingBlockMockups.save(mockup);

        return mockup.id();
    }

    public void editMockupDescription(MockupId mockupId, String description) {
        BuildingBlockMockup mockup = mockup(mockupId)
                .orElseThrow(() -> new MockupNotFoundException(mockupId));

        mockup.description(description);
    }

    public void deleteMockup(MockupId mockupId) {
        buildingBlockMockups.deleteById(mockupId);
    }

    public Collection<BuildingBlockMockup> mockupsByBuildingBlockId(BuildingBlockId buildingBlockId) {
        return buildingBlockMockups.findAllByBuildingBlockId(buildingBlockId);
    }

    private Optional<BuildingBlockMockup> mockup(MockupId mockupId) {
        return buildingBlockMockups.findById(mockupId);
    }

    public Collection<BuildingBlockMockup> mockupsByBuildingBlockId(Set<BuildingBlockId> buildingBlockIds) {
        return buildingBlockMockups.findAllByBuildingBlockIdIn(buildingBlockIds);
    }

    public Collection<BuildingBlockMockup> buildingBlockMockups(Set<MockupId> mockupIds) {
        return buildingBlockMockups.findAllById(mockupIds);
    }
}
