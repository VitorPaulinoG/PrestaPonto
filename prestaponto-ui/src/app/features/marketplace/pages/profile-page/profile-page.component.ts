import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';

import { AppMobileShellComponent } from '../../components/app-mobile-shell/app-mobile-shell.component';
import { BasicHeaderComponent } from '../../../../shared/components/basic-header/basic-header.component';
import { PageContentComponent } from '../../../../shared/components/page-content/page-content.component';
import { TokenService } from '../../../../core/services/token.service';
import { CLIENT_NAV_ITEMS, PROVIDER_NAV_ITEMS } from '../../models/marketplace.models';

@Component({
  selector: 'app-profile-page',
  imports: [AppMobileShellComponent, BasicHeaderComponent, PageContentComponent],
  templateUrl: './profile-page.component.html',
})
export class ProfilePageComponent {
  private readonly tokenService = inject(TokenService);
  private readonly router = inject(Router);

  protected readonly role = this.tokenService.getRole();
  protected readonly navItems = this.role === 'PROVIDER' ? PROVIDER_NAV_ITEMS : CLIENT_NAV_ITEMS;
  protected readonly activeNav = 'profile';

  protected logoff(): void {
    this.tokenService.removeToken();
    this.router.navigate(['/login']);
  }
}
