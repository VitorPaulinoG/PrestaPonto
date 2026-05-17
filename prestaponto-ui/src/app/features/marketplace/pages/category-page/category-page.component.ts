import { Component } from "@angular/core";
import { SectionBlockComponent } from "../../../../shared/components/section-block/section-block.component";
import { SearchBarComponent } from "../../components/search-bar/search-bar.component";
import { AppMobileShellComponent } from "../../components/app-mobile-shell/app-mobile-shell.component";
import { CategoryCardComponent } from "../../components/category-card/category-card.component";
import { CLIENT_NAV_ITEMS, HOME_CATEGORY_CARDS } from "../../models/marketplace.models";
import { PageContentComponent } from "../../../../shared/components/page-content/page-content.component";
import { FilterDropdown } from "../../../../shared/components/filter-dropdown/filter-dropdown";
import { HeroHeaderComponent } from "../../../../shared/components/hero-header/hero-header.component";
import { BottomSheetOption } from "../../../../shared/components/bottom-sheet-modal/bottom-sheet-modal.component";

@Component({
  selector: 'app-category-page',
  imports: [SectionBlockComponent, SearchBarComponent, AppMobileShellComponent, CategoryCardComponent, PageContentComponent, FilterDropdown, HeroHeaderComponent],
  templateUrl: './category-page.component.html'
})
export class CategoryPageComponent {
  protected readonly navItems = CLIENT_NAV_ITEMS;
  protected readonly categories = HOME_CATEGORY_CARDS;

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
}