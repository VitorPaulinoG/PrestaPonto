import { Component, inject, signal, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { AppMobileShellComponent } from '../../components/app-mobile-shell/app-mobile-shell.component';
import { BasicHeaderComponent } from '../../../../shared/components/basic-header/basic-header.component';
import { PageContentComponent } from '../../../../shared/components/page-content/page-content.component';
import { SectionBlockComponent } from '../../../../shared/components/section-block/section-block.component';
import { DisponibilidadeCardComponent } from '../../components/disponibilidade-card/disponibilidade-card.component';
import { Disponibilidade, DisponibilidadeService } from '../../services/disponibilidade.service';
import { CLIENT_NAV_ITEMS } from '../../models/marketplace.models';

@Component({
  selector: 'app-contracts-page',
  imports: [
    AppMobileShellComponent,
    BasicHeaderComponent,
    PageContentComponent,
    SectionBlockComponent,
    DisponibilidadeCardComponent,
  ],
  templateUrl: './contracts-page.component.html',
  styleUrl: './contracts-page.component.scss',
})
export class ContractsPageComponent implements OnInit {
  private readonly router = inject(Router);
  private readonly disponibilidadeService = inject(DisponibilidadeService);

  protected readonly navItems = CLIENT_NAV_ITEMS;
  protected readonly reservas = signal<Disponibilidade[]>([]);
  protected readonly loading = signal(false);

  ngOnInit(): void {
    this.loading.set(true);
    this.disponibilidadeService.getReservas(0, 50).subscribe({
      next: (resp) => {
        this.reservas.set(resp.content);
        this.loading.set(false);
      },
      error: () => {
        this.loading.set(false);
      },
    });
  }

  protected onBack(): void {
    this.router.navigate(['/home']);
  }
}
