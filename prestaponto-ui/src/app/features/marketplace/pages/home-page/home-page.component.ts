import { Component } from '@angular/core';

import { AppMobileShellComponent } from '../../components/app-mobile-shell/app-mobile-shell.component';
import { CategoryCardComponent } from '../../components/category-card/category-card.component';
import { ProviderListItemComponent } from '../../components/provider-list-item/provider-list-item.component';
import { SearchBarComponent } from '../../components/search-bar/search-bar.component';
import {
  CLIENT_NAV_ITEMS,
  FEATURED_PROVIDERS,
  HOME_CATEGORY_CARDS,
} from '../../models/marketplace.models';

@Component({
  selector: 'app-home-page',
  imports: [
    AppMobileShellComponent,
    SearchBarComponent,
    CategoryCardComponent,
    ProviderListItemComponent,
  ],
  templateUrl: './home-page.component.html',
  styleUrl: './home-page.component.scss',
})
export class HomePageComponent {
  protected readonly navItems = CLIENT_NAV_ITEMS;
  protected readonly categories = HOME_CATEGORY_CARDS;
  protected readonly providers = FEATURED_PROVIDERS;
}
