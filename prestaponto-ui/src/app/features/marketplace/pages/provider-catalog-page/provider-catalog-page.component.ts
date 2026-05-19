import { Component, inject, signal, effect } from '@angular/core';
import { RouterLink } from '@angular/router';
import { finalize } from 'rxjs';

import { AppMobileShellComponent } from '../../components/app-mobile-shell/app-mobile-shell.component';
import { HeroHeaderComponent } from '../../../../shared/components/hero-header/hero-header.component';
import { SearchBarComponent } from '../../components/search-bar/search-bar.component';
import { ServiceListItemComponent } from '../../components/service-list-item/service-list-item.component';
import { CatalogService, CatalogItem } from '../../services/catalog.service';
import { TokenService } from '../../../../core/services/token.service';
import { PROVIDER_NAV_ITEMS } from '../../models/marketplace.models';

@Component({
  selector: 'app-provider-catalog-page',
  imports: [RouterLink, AppMobileShellComponent, HeroHeaderComponent, SearchBarComponent, ServiceListItemComponent],
  templateUrl: './provider-catalog-page.component.html',
})
export class ProviderCatalogPageComponent {
  private readonly catalogService = inject(CatalogService);
  private readonly tokenService = inject(TokenService);

  protected readonly navItems = PROVIDER_NAV_ITEMS;
  protected readonly services = signal<CatalogItem[]>([]);
  protected readonly loading = signal(false);
  protected readonly searchName = signal('');

  constructor() {
    effect(() => {
      this.loadServices(this.searchName());
    });
  }

  onSearchChange(name: string): void {
    this.searchName.set(name);
  }

  private loadServices(name: string): void {
    const providerId = this.tokenService.getUserId();
    if (!providerId) return;

    this.loading.set(true);
    this.catalogService
      .findMyCatalogItems({ name: name || undefined, page: 0, size: 50 })
      .pipe(finalize(() => this.loading.set(false)))
      .subscribe({
        next: (page) => this.services.set(page.content),
      });
  }
}
