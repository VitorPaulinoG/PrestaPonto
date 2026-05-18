import { Component, inject } from "@angular/core";
import { SectionBlockComponent } from "../../../../shared/components/section-block/section-block.component";
import { AppMobileShellComponent } from "../../components/app-mobile-shell/app-mobile-shell.component";
import { CategoryCardComponent } from "../../components/category-card/category-card.component";
import { CLIENT_NAV_ITEMS, HOME_CATEGORY_CARDS } from "../../models/marketplace.models";
import { PageContentComponent } from "../../../../shared/components/page-content/page-content.component";
import { Router } from "@angular/router";

@Component({
  selector: 'app-category-page',
  imports: [SectionBlockComponent, AppMobileShellComponent, CategoryCardComponent, PageContentComponent],
  templateUrl: './category-page.component.html'
})
export class CategoryPageComponent {
  protected readonly navItems = CLIENT_NAV_ITEMS;
  protected readonly categories = HOME_CATEGORY_CARDS;
  private readonly router = inject(Router);

  protected currentFilter = 'providers'; 
  
  back() {
    this.router.navigate(['/home']);
  }
}