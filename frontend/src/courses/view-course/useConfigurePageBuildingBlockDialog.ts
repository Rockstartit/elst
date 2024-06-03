import { QDialogOptions } from 'quasar';
import ConfigurePageBuildingBlockDialog from 'src/courses/view-course/ConfigurePageBuildingBlockDialog.vue';

export interface ConfigurePageBuildingBlockDialogProps {
  pageBuildingBlockId: string;
}

export function configurePageBuildingBlockDialog(
  pageBuildingBlockId: string
): QDialogOptions {
  const dialogProps: ConfigurePageBuildingBlockDialogProps = {
    pageBuildingBlockId,
  };

  return {
    component: ConfigurePageBuildingBlockDialog,
    componentProps: dialogProps,
  };
}
