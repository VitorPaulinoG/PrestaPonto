import { Component, inject, signal } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { AppMobileShellComponent } from '../../components/app-mobile-shell/app-mobile-shell.component';
import { BasicHeaderComponent } from '../../../../shared/components/basic-header/basic-header.component';
import { PageContentComponent } from '../../../../shared/components/page-content/page-content.component';
import { CLIENT_NAV_ITEMS } from '../../models/marketplace.models';
import { CatalogItem, CatalogService } from '../../services/catalog.service';

@Component({
  selector: 'app-catalog-item-page',
  imports: [AppMobileShellComponent, BasicHeaderComponent, PageContentComponent],
  templateUrl: './catalog-item-page.component.html',
  styleUrl: './catalog-item-page.component.scss',
})
export class CatalogItemPageComponent {
  private readonly router = inject(Router);
  private readonly route = inject(ActivatedRoute);
  private readonly catalogService = inject(CatalogService);

  protected readonly navItems = CLIENT_NAV_ITEMS;

  protected readonly item = signal<CatalogItem | null>(null);
  protected readonly catalogItemId = signal<string>('');

  constructor() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.catalogItemId.set(id);
      this.catalogService.findById(id).subscribe({
        next: (data) => {
          this.item.set(data);
        },
      });
    }
  }

  protected onVerDisponibilidades(): void {
    const item = this.item();
    if (!item || !item.providerId) return;
    this.router.navigate(['/disponibilidades', item.providerId], {
      queryParams: { catalogItemId: item.id },
    });
  }

  protected onBack(): void {
    this.router.navigate(['/explore']);
  }

}