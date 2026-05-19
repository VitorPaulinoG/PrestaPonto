import { Component } from '@angular/core';

@Component({
  selector: 'app-filter-bar',
  imports: [],
  template: ` 
  <div class="flex p-2.5 gap-2.5 h-auto w-full bg-white border-b border-primary-02">
    <ng-content />
  </div>
  `,
})
export class FilterBarComponent {}
