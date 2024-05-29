import { BuildingBlockProperty } from 'src/services/generated/openapi';
import { QDialogOptions } from 'quasar';
import CreateOrEditPropertyDialog from 'src/building-blocks/view-building-block/properties/CreateOrEditPropertyDialog.vue';

export interface CreateOrEditPropertyDialogProps {
  property?: BuildingBlockProperty;
}

export interface CreateOrEditPropertyDialogResult {
  key: string;
  displayName: string;
  description: string;
}

export function createPropertyDialog(): QDialogOptions {
  return {
    component: CreateOrEditPropertyDialog,
  };
}

export function editPropertyDialog(
  property: BuildingBlockProperty
): QDialogOptions {
  const dialogProps: CreateOrEditPropertyDialogProps = {
    property,
  };

  return {
    component: CreateOrEditPropertyDialog,
    componentProps: dialogProps,
  };
}
