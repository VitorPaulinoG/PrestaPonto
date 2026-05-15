import { Component, input } from '@angular/core';

import { AppNavId, BottomNavItem } from '../../models/marketplace.models';
import { BottomNavComponent } from '../bottom-nav/bottom-nav.component';

@Component({
  selector: 'app-mobile-shell',
  imports: [BottomNavComponent],
  templateUrl: './app-mobile-shell.component.html',
  styleUrl: './app-mobile-shell.component.scss',
})
export class AppMobileShellComponent {
  readonly navItems = input.required<BottomNavItem[]>();
  readonly activeNav = input.required<AppNavId>();
}
