import { Component, input } from '@angular/core';
import { RouterLink } from '@angular/router';

import { AppNavId, BottomNavItem } from '../../models/marketplace.models';

@Component({
  selector: 'app-bottom-nav',
  imports: [RouterLink],
  templateUrl: './bottom-nav.component.html',
  styleUrl: './bottom-nav.component.scss',
})
export class BottomNavComponent {
  readonly items = input.required<BottomNavItem[]>();
  readonly activeItem = input.required<AppNavId>();
}
