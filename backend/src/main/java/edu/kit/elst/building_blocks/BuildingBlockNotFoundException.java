package edu.kit.elst.building_blocks;

public class BuildingBlockNotFoundException extends RuntimeException {
    public BuildingBlockNotFoundException(BuildingBlockId id) {
        super(String.format("building block %s does not exist", id));
    }
}
