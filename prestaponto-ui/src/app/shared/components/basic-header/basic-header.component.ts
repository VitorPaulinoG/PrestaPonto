import { Component, input, output } from '@angular/core';

@Component({
  selector: 'app-basic-header',
  imports: [],
  template: `
    <header class="flex items-center p-4 gap-2.5 bg-primary-02 text-white">
      @if (showBackButton()) {
        <span
          class="material-symbols-outlined hover:cursor-pointer"
          aria-hidden="true"
          (click)="back.emit()"
        >
          arrow_left_alt
        </span>
      }
      <span class="text-base font-semibold">{{ title() }}</span>
      <ng-content />
    </header>
  `,
})
export class BasicHeaderComponent {
  readonly title = input.required<string>();
  readonly showBackButton = input<boolean>(true);
  readonly back = output<void>();
}
