import { Injectable } from '@angular/core';

export interface CatalogItem {
  id: string;
  title: string;
  category: string;
  providerName: string;
  price: string;
  rating: number;
  description: string;
}

@Injectable({ providedIn: 'root' })
export class CatalogService {
  private readonly items: CatalogItem[] = [
    {
      id: '1',
      title: 'Aterramento Elétrico',
      category: 'Instalações Elétricas',
      providerName: 'Fulano de Tal',
      price: 'R$ 90,00',
      rating: 4.9,
      description:
        'Serviço completo de aterramento elétrico residencial e comercial. Inclui instalação de haste de cobre, medição de resistência e laudo técnico.',
    },
    {
      id: '2',
      title: 'Instalação de Tomadas',
      category: 'Instalações Elétricas',
      providerName: 'Beltrano de Tal',
      price: 'R$ 55,00',
      rating: 4.8,
      description:
        'Instalação de tomadas e interruptores com fiação dedicada e proteção adequada. Atendimento rápido e garantia de 90 dias.',
    },
    {
      id: '3',
      title: 'Pintura Residencial',
      category: 'Pintura',
      providerName: 'Cicrano de Tal',
      price: 'R$ 200,00',
      rating: 4.7,
      description:
        'Pintura interna e externa com tintas de qualidade. Preparo de superfície, lixamento e acabamento profissional.',
    },
    {
      id: '4',
      title: 'Faxina e Limpeza',
      category: 'Limpeza',
      providerName: 'Placeholder',
      price: 'R$ 120,00',
      rating: 4.5,
      description:
        'Limpeza completa de residências e escritórios. Produtos ecologicamente corretos e equipe treinada.',
    },
    {
      id: '5',
      title: 'Encanador 24h',
      category: 'Hidráulica',
      providerName: 'Anonymous',
      price: 'R$ 150,00',
      rating: 4.3,
      description:
        'Atendimento emergencial de encanamento 24 horas. Conserto de vazamentos, desentupimento e troca de torneiras.',
    },
  ];

  getAll(): CatalogItem[] {
    return this.items;
  }

  getById(id: string): CatalogItem | undefined {
    return this.items.find((i) => i.id === id);
  }
}
