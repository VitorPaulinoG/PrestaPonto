import { Component, input } from '@angular/core';

@Component({
  selector: 'app-provider-list-item',
  templateUrl: './provider-list-item.component.html',
  styleUrl: './provider-list-item.component.scss',
})
export class ProviderListItemComponent {
  readonly initials = input.required<string>();
  readonly name = input.required<string>();
  readonly category = input.required<string>();
  readonly rating = input.required<number>();
}
