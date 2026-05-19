export type AuthRole = 'PROVIDER' | 'CLIENT';

export type AuthTab = 'login' | 'signup';

export interface AuthRoleOption {
  label: string;
  value: AuthRole;
}

export const AUTH_ROLE_OPTIONS: AuthRoleOption[] = [
  {
    label: 'Prestador',
    value: 'PROVIDER',
  },
  {
    label: 'Cliente',
    value: 'CLIENT',
  },
];
