import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

import { AppMobileShellComponent } from '../../components/app-mobile-shell/app-mobile-shell.component';
import { SearchBarComponent } from '../../components/search-bar/search-bar.component';
import { ServiceListItemComponent } from '../../components/service-list-item/service-list-item.component';
import { PROVIDER_NAV_ITEMS, PROVIDER_SERVICES } from '../../models/marketplace.models';

@Component({
  selector: 'app-provider-catalog-page',
  imports: [RouterLink, AppMobileShellComponent, SearchBarComponent, ServiceListItemComponent],
  templateUrl: './provider-catalog-page.component.html',
  styleUrl: './provider-catalog-page.component.scss',
})
export class ProviderCatalogPageComponent {
  protected readonly navItems = PROVIDER_NAV_ITEMS;
  protected readonly services = PROVIDER_SERVICES;
}
