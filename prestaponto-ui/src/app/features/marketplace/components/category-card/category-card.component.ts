import { Component, input, output } from '@angular/core';

@Component({
  selector: 'app-category-card',
  templateUrl: './category-card.component.html',
  styleUrl: './category-card.component.scss',
})
export class CategoryCardComponent {
  readonly title = input.required<string>();
  readonly icon = input.required<string>();

  categorySelected = output<string>();
}
