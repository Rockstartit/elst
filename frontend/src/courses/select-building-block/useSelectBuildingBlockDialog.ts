import { BuildingBlock } from 'src/services/generated/openapi';
import { QDialogOptions } from 'quasar';
import BrowseBuildingBlocksDialog from 'src/courses/select-building-block/SelectBuildingBlocksDialog.vue';

export interface SelectBuildingBlockDialogProps {
  technologyWish: string;
}

export interface SelectBuildingBlockDialogResult {
  buildingBlock: BuildingBlock;
}

export function selectBuildingBlockDialog(
  technologyWish: string
): QDialogOptions {
  const dialogProps: SelectBuildingBlockDialogProps = {
    technologyWish,
  };

  return {
    component: BrowseBuildingBlocksDialog,
    componentProps: dialogProps,
  };
}
