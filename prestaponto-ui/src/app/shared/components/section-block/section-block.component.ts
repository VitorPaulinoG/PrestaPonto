import { Component, input } from '@angular/core';

@Component({
  selector: 'app-section-block',
  imports: [],
  templateUrl: './section-block.component.html'
})
export class SectionBlockComponent {
  readonly title = input.required<string>();
  readonly linkText = input<string>();
  readonly linkHref = input<string>('#');
  readonly maxHeight = input<string>('16rem');
}
