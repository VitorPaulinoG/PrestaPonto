import { Component, input } from '@angular/core';

@Component({
  selector: 'app-service-list-item',
  templateUrl: './service-list-item.component.html',
  styleUrl: './service-list-item.component.scss',
})
export class ServiceListItemComponent {
  readonly title = input.required<string>();
  readonly category = input.required<string>();
  readonly price = input.required<string>();
}
