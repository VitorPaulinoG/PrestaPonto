import { Component, effect, inject, signal } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

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
import { CatalogItem, CatalogService } from '../../services/catalog.service';

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
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);
  private readonly catalogService = inject(CatalogService);

  protected readonly searchQuery = signal<string>('');
  protected readonly navItems = CLIENT_NAV_ITEMS;

  protected readonly options: BottomSheetOption[] = [
    { label: 'Serviços', value: 'services' },
    { label: 'Prestadores', value: 'providers' },
    { label: 'Categorias', value: 'categories' },
  ];

  protected currentFilter = 'services';
  protected readonly services: CatalogItem[] = this.catalogService.getAll();

  constructor() {
    effect(() => {
      const params = this.route.snapshot.queryParams;
      const q = params['q'];
      const filter = params['filter'];
      if (q) {
        this.searchQuery.set(q);
      }
      if (filter && this.options.some((o) => o.value === filter)) {
        this.currentFilter = filter;
      }
    });
  }

  protected getCurrentOption(): BottomSheetOption | undefined {
    return this.options.find((x) => x.value === this.currentFilter);
  }

  protected onFilterChange(value: string): void {
    this.currentFilter = value;
  }

  protected onCardClick(service: CatalogItem): void {
    this.router.navigate(['/catalog-item', service.id]);
  }
}