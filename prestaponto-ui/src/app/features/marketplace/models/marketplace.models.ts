export type AppNavId = 'home' | 'explore' | 'contracts' | 'profile' | 'catalog' | 'schedule' | 'history' | 'provider';

export interface BottomNavItem {
  id: AppNavId;
  label: string;
  route: string;
  icon: AppNavId;
}

export interface CategoryCard {
  title: string;
  icon: 'build' | 'beauty' | 'computer' | 'events' | 'cleaning' | 'care';
}

export interface ProviderHighlight {
  initials: string;
  name: string;
  category: string;
  rating: number;
}

export interface ServiceCatalogItem {
  title: string;
  category: string;
  price: string;
}

export const CLIENT_NAV_ITEMS: BottomNavItem[] = [
  { id: 'home', label: 'Início', route: '/home', icon: 'home' },
  { id: 'explore', label: 'Explorar', route: '/home', icon: 'explore' },
  { id: 'contracts', label: 'Contratos', route: '/home', icon: 'contracts' },
  { id: 'profile', label: 'Perfil', route: '/home', icon: 'profile' },
];

export const PROVIDER_NAV_ITEMS: BottomNavItem[] = [
  { id: 'catalog', label: 'Catálogo', route: '/provider/catalog', icon: 'catalog' },
  { id: 'schedule', label: 'Agenda', route: '/provider/catalog', icon: 'schedule' },
  { id: 'history', label: 'Histórico', route: '/provider/catalog', icon: 'history' },
  { id: 'provider', label: 'Prestador', route: '/provider/catalog', icon: 'provider' },
];

export const HOME_CATEGORY_CARDS: CategoryCard[] = [
  { title: 'Casa & Reforma', icon: 'build' },
  { title: 'Beleza & Bem-Estar', icon: 'beauty' },
  { title: 'Tecnologia & Informática', icon: 'computer' },
  { title: 'Eventos', icon: 'events' },
  { title: 'Limpeza', icon: 'cleaning' },
  { title: 'Cuidados', icon: 'care' },
];

export const FEATURED_PROVIDERS: ProviderHighlight[] = [
  { initials: 'FT', name: 'Fulano de Tal', category: 'Instalações Elétricas', rating: 4.9 },
  { initials: 'BT', name: 'Beltrano de Tal', category: 'Pintura Residencial', rating: 4.8 },
  { initials: 'CT', name: 'Cicrano de Tal', category: 'Limpeza de Equipamentos', rating: 4.8 },
  { initials: 'PL', name: 'Placeholder', category: 'Costura', rating: 4.2 },
  { initials: 'CT', name: 'Anonymous', category: 'Instalações Hidráulicas', rating: 3.5 },
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
