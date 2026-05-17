import { Component } from '@angular/core';

@Component({
  selector: 'app-page-content',
  imports: [],
  template: `
    <main class="flex-1 flex flex-col gap-[var(--spacing-sm)] p-[var(--spacing-md)]">
      <ng-content />
    </main>
  `,
})
export class PageContentComponent {}
