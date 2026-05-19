import { Component, input } from '@angular/core';

@Component({
  selector: 'app-explore-service-card',
  imports: [],
  templateUrl: './explore-service-card.component.html',
  styleUrl: './explore-service-card.component.scss',
})
export class ExploreServiceCardComponent {
  readonly serviceName = input.required<string>();
  readonly category = input.required<string>();
  readonly providerName = input.required<string>();
  readonly price = input.required<string>();
}
