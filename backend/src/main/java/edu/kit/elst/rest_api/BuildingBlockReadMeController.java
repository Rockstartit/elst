package edu.kit.elst.rest_api;

import edu.kit.elst.core.shared.BuildingBlockId;
import edu.kit.elst.building_blocks.ReadMeAppService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@AllArgsConstructor
public class BuildingBlockReadMeController implements BuildingBlockReadMeApi {
    private final ReadMeAppService readMeAppService;

    @Override
    public ResponseEntity<Void> editBuildingBlockReadMe(UUID buildingBlockId, EditReadMeRequest body) {
        BuildingBlockId aBuildingBlockId = new BuildingBlockId(buildingBlockId);

        readMeAppService.editReadMe(aBuildingBlockId, body.getContent());

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<String> getBuildingBlockReadMe(UUID buildingBlockId) {
        BuildingBlockId aBuildingBlockId = new BuildingBlockId(buildingBlockId);

        String content = readMeAppService.getReadMe(aBuildingBlockId);

        return ResponseEntity.ok(content);
    }
}
