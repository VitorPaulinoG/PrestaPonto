import { Component, input } from '@angular/core';
import { RouterLink } from '@angular/router';

import { AuthTab } from '../../models/auth.models';
import { BrandLogoComponent } from '../brand-logo/brand-logo.component';

@Component({
  selector: 'app-auth-shell',
  imports: [RouterLink, BrandLogoComponent],
  templateUrl: './auth-shell.component.html',
  styleUrl: './auth-shell.component.scss',
})
export class AuthShellComponent {
  readonly activeTab = input.required<AuthTab>();
}
