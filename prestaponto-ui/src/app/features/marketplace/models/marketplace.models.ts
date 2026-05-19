
export interface BottomNavItem {
  id: string;
  label: string;
  route: string;
  icon: string;
}

export interface CategoryCard {
  title: string;
  icon: string;
}

export interface ProviderHighlight {
  initials: string;
  name: string;
  category: string;
  rating: number;
}

export interface ServiceCatalogItem {
  id: string;
  title: string;
  category: string;
  price: string;
  description: string;
}

export const CLIENT_NAV_ITEMS: BottomNavItem[] = [
  { id: 'home', label: 'Início', route: '/home', icon: 'home' },
  { id: 'explore', label: 'Explorar', route: '/explore', icon: 'search' },
  { id: 'contracts', label: 'Contratos', route: '/contracts', icon: 'contract' },
  { id: 'profile', label: 'Perfil', route: '/home', icon: 'person' },
];

export const PROVIDER_NAV_ITEMS: BottomNavItem[] = [
  { id: 'catalog', label: 'Catálogo', route: '/provider/catalog', icon: 'menu_book' },
  { id: 'contracts', label: 'Contratos', route: '/provider/contracts', icon: 'contract' },
  { id: 'agenda', label: 'Agenda', route: '/provider/agenda', icon: 'calendar_month' },
  { id: 'profile', label: 'Perfil', route: '/home', icon: 'person' },
];

export const HOME_CATEGORY_CARDS: CategoryCard[] = [
  // Manutenção e Reformas Domésticas
  { title: 'Instalações Elétricas', icon: 'electrical_services' },
  { title: 'Encanador e Hidráulica', icon: 'plumbing' },
  { title: 'Pintura residencial', icon: 'format_paint' },
  { title: 'Pedreiro e Alvenaria', icon: 'construction' },
  { title: 'Marcenaria e Móveis', icon: 'carpenter' },
  { title: 'Chaveiro 24h', icon: 'vpn_key' },
  { title: 'Instalação de Ar Condicionado', icon: 'ac_unit' },
  { title: 'Montagem de Móveis', icon: 'handyman' },
  { title: 'Vidraçaria', icon: 'window' },
  { title: 'Gesso e Drywall', icon: 'layers' },

  // Limpeza e Cuidados com a Casa
  { title: 'Faxina e Limpeza', icon: 'cleaning_services' },
  { title: 'Jardinagem e Paisagismo', icon: 'yard' },
  { title: 'Dedetização', icon: 'pest_control' },
  { title: 'Limpeza de Estofados', icon: 'chair' },
  { title: 'Lavanderia e Passadoria', icon: 'local_laundry_service' },
  { title: 'Organização de Ambientes', icon: 'shelves' },

  // Assistência Técnica e Tecnologia
  { title: 'Conserto de Computadores', icon: 'computer' },
  { title: 'Conserto de Celulares', icon: 'smartphone' },
  { title: 'Manutenção de Eletrodomésticos', icon: 'microwave_gen' },
  { title: 'Redes e Wi-Fi', icon: 'router' },
  { title: 'Câmeras e Segurança', icon: 'videocam' },

  // Bem-estar, Saúde e Estética
  { title: 'Cabelereiro e Barbearia', icon: 'content_cut' },
  { title: 'Manicure e Pedicure', icon: 'health_and_beauty' },
  { title: 'Maquiagem e Estética', icon: 'face_5' },
  { title: 'Massoterapia', icon: 'spa' },
  { title: 'Personal Trainer', icon: 'fitness_center' },
  { title: 'Fisioterapia Doméstica', icon: 'physical_therapy' },
  { title: 'Cuidador de Idosos', icon: 'elderly' },

  // Serviços para Pets
  { title: 'Banho e Tosa', icon: 'pets' },
  { title: 'Adestramento', icon: 'sound_detection_dog_barking' },
  { title: 'Hospedagem de Animais', icon: 'home' },

  // Eventos e Gastronomia
  { title: 'Buffet e Catering', icon: 'restaurant' },
  { title: 'Confeitaria e Doces', icon: 'cake' },
  { title: 'Fotografia e Filmagem', icon: 'photo_camera' },
  { title: 'Organização de Festas', icon: 'celebration' },
  { title: 'Segurança para Eventos', icon: 'shield' },

  // Automotivo
  { title: 'Mecânica Geral', icon: 'car_repair' },
  { title: 'Lava Jato', icon: 'local_car_wash' },
  { title: 'Guincho e Reboque', icon: 'rv_hookup' },

  // Aulas e Consultoria
  { title: 'Aulas Particulares', icon: 'school' },
  { title: 'Consultoria Financeira', icon: 'finance_mode' },
  { title: 'Tradução de Textos', icon: 'translate' }
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
    id: '1',
    title: 'Aterramento Elétrico',
    category: 'Instalações Elétricas',
    price: 'R$ 90,00',
    description: 'Serviço completo de aterramento elétrico residencial e comercial.',
  },
  {
    id: '2',
    title: 'Instalação de tomadas e interruptores',
    category: 'Instalações Elétricas',
    price: 'R$ 55,00',
    description: 'Instalação de tomadas e interruptores com fiação dedicada e proteção.',
  },
];
