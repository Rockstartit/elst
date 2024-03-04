package edu.kit.elst.building_blocks;

public class BuildingBlockNotFoundException extends RuntimeException {
    public BuildingBlockNotFoundException(BuildingBlockVersion version) {
        super(String.format("building block %s does not exist", version));
    }
}
