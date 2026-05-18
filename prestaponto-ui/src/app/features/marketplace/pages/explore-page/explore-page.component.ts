import { Component } from '@angular/core';

import { HeroHeaderComponent } from '../../../../shared/components/hero-header/hero-header.component';
import { PageContentComponent } from '../../../../shared/components/page-content/page-content.component';
import { SectionBlockComponent } from '../../../../shared/components/section-block/section-block.component';
import { FilterBarComponent } from '../../../../shared/components/filter-bar/filter-bar.component';
import { BottomSheetOption } from '../../../../shared/components/bottom-sheet-modal/bottom-sheet-modal.component';
import { AppMobileShellComponent } from '../../components/app-mobile-shell/app-mobile-shell.component';
import { ExploreServiceCardComponent } from '../../components/explore-service-card/explore-service-card.component';
import { FilterDropdown } from '../../../../shared/components/filter-dropdown/filter-dropdown';
import { SearchBarComponent } from '../../components/search-bar/search-bar.component';
import { CLIENT_NAV_ITEMS } from '../../models/marketplace.models';

export interface ExploreServiceItem {
  serviceName: string;
  category: string;
  providerName: string;
  price: string;
  rating: number;
}

@Component({
  selector: 'app-explore-page',
  imports: [
    AppMobileShellComponent,
    HeroHeaderComponent,
    SearchBarComponent,
    FilterBarComponent,
    FilterDropdown,
    PageContentComponent,
    SectionBlockComponent,
    ExploreServiceCardComponent,
  ],
  templateUrl: './explore-page.component.html',
  styleUrl: './explore-page.component.scss',
})
export class ExplorePageComponent {
  protected readonly navItems = CLIENT_NAV_ITEMS;

  protected readonly options: BottomSheetOption[] = [
    { label: 'Serviços', value: 'services' },
    { label: 'Prestadores', value: 'providers' },
    { label: 'Categorias', value: 'categories' },
  ];

  protected currentFilter = 'services';

  protected readonly services: ExploreServiceItem[] = [
    {
      serviceName: 'Aterramento Elétrico',
      category: 'Instalações Elétricas',
      providerName: 'Fulano de Tal',
      price: 'R$ 90,00',
      rating: 4.9,
    },
    {
      serviceName: 'Instalação de Tomadas',
      category: 'Instalações Elétricas',
      providerName: 'Beltrano de Tal',
      price: 'R$ 55,00',
      rating: 4.8,
    },
    {
      serviceName: 'Pintura Residencial',
      category: 'Pintura',
      providerName: 'Cicrano de Tal',
      price: 'R$ 200,00',
      rating: 4.7,
    },
    {
      serviceName: 'Faxina e Limpeza',
      category: 'Limpeza',
      providerName: 'Placeholder',
      price: 'R$ 120,00',
      rating: 4.5,
    },
    {
      serviceName: 'Encanador 24h',
      category: 'Hidráulica',
      providerName: 'Anonymous',
      price: 'R$ 150,00',
      rating: 4.3,
    },
  ];

  protected getCurrentOption(): BottomSheetOption | undefined {
    return this.options.find((x) => x.value === this.currentFilter);
  }

  protected onFilterChange(value: string): void {
    this.currentFilter = value;
  }
}
