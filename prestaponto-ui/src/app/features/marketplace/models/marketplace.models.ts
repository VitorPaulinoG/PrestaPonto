export type AppNavId = 'home' | 'explore' | 'contracts' | 'profile' | 'catalog' | 'schedule' | 'history' | 'provider';

export interface BottomNavItem {
  id: AppNavId;
  label: string;
  route: string;
  icon: AppNavId;
}

export interface ServiceCatalogItem {
  title: string;
  category: string;
  price: string;
}

export const PROVIDER_NAV_ITEMS: BottomNavItem[] = [
  { id: 'catalog', label: 'Catálogo', route: '/provider/catalog', icon: 'catalog' },
  { id: 'schedule', label: 'Agenda', route: '/provider/catalog', icon: 'schedule' },
  { id: 'history', label: 'Histórico', route: '/provider/catalog', icon: 'history' },
  { id: 'provider', label: 'Prestador', route: '/provider/catalog', icon: 'provider' },
];

export const PROVIDER_SERVICES: ServiceCatalogItem[] = [
  {
    title: 'Aterramento Elétrico',
    category: 'Instalações Elétricas',
    price: 'R$ 90,00',
  },
  {
    title: 'Instalação de tomadas e interruptores',
    category: 'Instalações Elétricas',
    price: 'R$ 55,00',
  },
];
