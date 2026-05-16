import { Component } from '@angular/core';

import { BrandLogoComponent } from '../../../../shared/components/brand-logo/brand-logo.component';
import { TabsComponent } from '../../../../shared/components/tabs/tabs.component';

@Component({
  selector: 'app-auth-shell',
  imports: [BrandLogoComponent, TabsComponent],
  templateUrl: './auth-shell.component.html',
  styleUrl: './auth-shell.component.scss',
})
export class AuthShellComponent {}
