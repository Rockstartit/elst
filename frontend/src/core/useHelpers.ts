export function fullName(user: { firstName?: string; lastName?: string }) {
  const fullName = [user.firstName, user.lastName].join(' ').trim();

  if (fullName.length === 0) {
    return 'Anonym';
  }

  return fullName;
}
