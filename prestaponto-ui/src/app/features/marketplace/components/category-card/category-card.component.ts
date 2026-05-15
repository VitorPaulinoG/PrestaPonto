import { Component, input } from '@angular/core';

@Component({
  selector: 'app-category-card',
  templateUrl: './category-card.component.html',
  styleUrl: './category-card.component.scss',
})
export class CategoryCardComponent {
  readonly title = input.required<string>();
  readonly icon = input.required<'build' | 'beauty' | 'computer' | 'events' | 'cleaning' | 'care'>();
}
