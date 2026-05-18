import { Component, input } from '@angular/core';

@Component({
  selector: 'app-hero-header',
  imports: [],
  template: `
    <header
      class="flex flex-col justify-end gap-[var(--spacing-sm)] min-h-[11.5rem] pt-2.5 px-[var(--spacing-md)] pb-[var(--spacing-md)] bg-[var(--color-primary-02)]"
    >
      @if (title()) {
        <h1 class="m-0 text-[var(--color-white)] text-[1.75rem] font-semibold leading-[1.4]">
          {{ title() }}
        </h1>
      }
      <ng-content />
    </header>
  `,
})
export class HeroHeaderComponent {
  readonly title = input<string>();
}
