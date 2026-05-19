import { Component, input } from '@angular/core';

@Component({
  selector: 'app-brand-logo',
  templateUrl: './brand-logo.component.html',
})
export class BrandLogoComponent {
  readonly inverted = input(false);
  readonly compact = input(false);
}
