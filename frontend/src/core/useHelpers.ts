import { User } from 'src/services/generated/openapi';

export function fullName(user: User) {
  const fullName = [user.firstName, user.lastName].join(' ').trim();

  if (fullName.length === 0) {
    return 'Anonym';
  }

  return fullName;
}
