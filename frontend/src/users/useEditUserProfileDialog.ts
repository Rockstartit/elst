import { QDialogOptions } from 'quasar';
import EditUserProfileDialog from 'src/users/EditUserProfileDialog.vue';
import { UserProfile } from 'src/services/generated/openapi';

export interface EditUserProfileDialogProps {
  userProfile: UserProfile;
}

export interface EditUserProfileDialogResult {
  id: string;
  firstName?: string;
  lastName?: string;
}

export function editUserProfileDialog(
  dialogProps: EditUserProfileDialogProps
): QDialogOptions {
  return {
    component: EditUserProfileDialog,
    componentProps: dialogProps,
  };
}
