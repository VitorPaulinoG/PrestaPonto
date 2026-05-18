import { Component, inject } from "@angular/core";
import { SectionBlockComponent } from "../../../../shared/components/section-block/section-block.component";
import { AppMobileShellComponent } from "../../components/app-mobile-shell/app-mobile-shell.component";
import { CategoryCardComponent } from "../../components/category-card/category-card.component";
import { CLIENT_NAV_ITEMS, HOME_CATEGORY_CARDS } from "../../models/marketplace.models";
import { PageContentComponent } from "../../../../shared/components/page-content/page-content.component";
import { FilterBarComponent } from "../../../../shared/components/filter-bar/filter-bar.component";
import { FilterDropdown } from "../../../../shared/components/filter-dropdown/filter-dropdown";
import { BottomSheetOption } from "../../../../shared/components/bottom-sheet-modal/bottom-sheet-modal.component";
import { Router } from "@angular/router";

@Component({
  selector: 'app-category-page',
  imports: [SectionBlockComponent, AppMobileShellComponent, CategoryCardComponent, PageContentComponent, FilterBarComponent, FilterDropdown],
  templateUrl: './category-page.component.html'
})
export class CategoryPageComponent {
  protected readonly navItems = CLIENT_NAV_ITEMS;
  protected readonly categories = HOME_CATEGORY_CARDS;
  private readonly router = inject(Router);

  protected readonly options: BottomSheetOption[] = [
    { label: 'Serviços', value: 'services' },
    { label: 'Prestadores', value: 'providers' },
    { label: 'Categorias', value: 'categories' },
  ];

  protected currentFilter = 'providers'; 
  
  getCurrentOption() {
    return this.options.filter(x => x.value == this.currentFilter)?.at(0);
  }
  
  onFilterChange(value: string): void {                                                                                              
    this.currentFilter = value;                                                                                                      
    // providers | categories — decide o que renderizar                                                                              
  } 

  back() {
    this.router.navigate(['/home']);
  }
}