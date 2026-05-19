import { Component, inject, OnInit, OnDestroy, signal, ElementRef, viewChild } from '@angular/core';
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
export class ExplorePageComponent implements OnInit, OnDestroy {
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);
  private readonly catalogService = inject(CatalogService);

  protected readonly navItems = CLIENT_NAV_ITEMS;

  protected readonly searchQuery = signal<string>('');
  protected readonly page = signal(0);
  protected readonly size = 10;
  protected readonly items = signal<CatalogItem[]>([]);
  protected readonly hasMore = signal(true);
  protected readonly loading = signal(false);

  protected readonly options: BottomSheetOption[] = [
    { label: 'Serviços', value: 'services' },
    { label: 'Prestadores', value: 'providers' },
    { label: 'Categorias', value: 'categories' },
  ];

  protected currentFilter = 'services';

  protected readonly sentinel = viewChild<ElementRef<HTMLDivElement>>('sentinel');
  private observer?: IntersectionObserver;

  ngOnInit(): void {
    const params = this.route.snapshot.queryParams;
    const q = params['q'] ?? '';
    const filter = params['filter'];
    this.searchQuery.set(q);
    if (filter && this.options.some((o) => o.value === filter)) {
      this.currentFilter = filter;
    }
    this.resetState();
    this.loadPage(0);
    setTimeout(() => this.setupIntersectionObserver(), 100);
  }

  private loadPage(pageNum: number): void {
    if (this.loading() || !this.hasMore()) return;
    this.loading.set(true);

    const filterParams: { name?: string; category?: string; providerName?: string; page: number; size: number } = {
      page: pageNum,
      size: this.size,
    };

    if (this.currentFilter === 'categories') {
      filterParams.category = this.searchQuery();
    } else if (this.currentFilter === 'services') {
      filterParams.name = this.searchQuery();
    } else if (this.currentFilter === 'providers') {
      filterParams.providerName = this.searchQuery();
    }

    this.catalogService.findByFilter(filterParams).subscribe({
      next: (resp) => {
        this.items.update((prev) => (pageNum === 0 ? resp.content : [...prev, ...resp.content]));
        this.hasMore.set(!resp.last);
        this.loading.set(false);
      },
      error: () => {
        this.loading.set(false);
      },
    });
  }

  private setupIntersectionObserver(): void {
    const el = this.sentinel()?.nativeElement;
    if (!el) return;
    this.observer?.disconnect();
    this.observer = new IntersectionObserver((entries) => {
      if (entries[0]?.isIntersecting && this.hasMore() && !this.loading()) {
        const next = this.page() + 1;
        this.page.set(next);
        this.loadPage(next);
      }
    });
    this.observer.observe(el);
  }

  protected onFilterChange(value: string): void {
    this.currentFilter = value;
    this.router.navigate(['/explore'], {
      queryParams: { q: this.searchQuery(), filter: value },
      queryParamsHandling: 'merge',
    });
    this.resetState();
    this.loadPage(0);
  }

  protected onCardClick(service: CatalogItem): void {
    this.router.navigate(['/catalog-item', service.id]);
  }

  protected getCurrentOption(): BottomSheetOption | undefined {
    return this.options.find((o) => o.value === this.currentFilter);
  }

  protected onSearch(query: string): void {
    this.searchQuery.set(query);
    this.router.navigate(['/explore'], {
      queryParams: { q: query, filter: this.currentFilter },
      queryParamsHandling: 'merge',
    });
    this.resetState();
    this.loadPage(0);
  }

  private resetState(): void {
    this.page.set(0);
    this.items.set([]);
    this.hasMore.set(true);
  }

  ngOnDestroy(): void {
    this.observer?.disconnect();
  }
}
