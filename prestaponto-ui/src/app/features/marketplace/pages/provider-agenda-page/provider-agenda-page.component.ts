import { Component, OnInit, inject, signal } from '@angular/core';
import { Router } from '@angular/router';
import { finalize } from 'rxjs';

import { AppMobileShellComponent } from '../../components/app-mobile-shell/app-mobile-shell.component';
import { BasicHeaderComponent } from '../../../../shared/components/basic-header/basic-header.component';
import { PageContentComponent } from '../../../../shared/components/page-content/page-content.component';
import { SectionBlockComponent } from '../../../../shared/components/section-block/section-block.component';
import { DisponibilidadeCardComponent } from '../../components/disponibilidade-card/disponibilidade-card.component';
import { DisponibilidadeService, Disponibilidade } from '../../services/disponibilidade.service';
import { PROVIDER_NAV_ITEMS } from '../../models/marketplace.models';

@Component({
  selector: 'app-provider-agenda-page',
  imports: [
    AppMobileShellComponent,
    BasicHeaderComponent,
    PageContentComponent,
    SectionBlockComponent,
    DisponibilidadeCardComponent,
  ],
  templateUrl: './provider-agenda-page.component.html',
})
export class ProviderAgendaPageComponent implements OnInit {
  private readonly disponibilidadeService = inject(DisponibilidadeService);
  private readonly router = inject(Router);

  protected readonly navItems = PROVIDER_NAV_ITEMS;
  protected readonly disponibilidades = signal<Disponibilidade[]>([]);
  protected readonly loading = signal(false);
  protected readonly error = signal<string | null>(null);

  ngOnInit(): void {
    this.loadReservadas();
  }

  protected onBack(): void {
    this.router.navigate(['/provider/catalog']);
  }

  private loadReservadas(): void {
    this.loading.set(true);
    this.disponibilidadeService
      .getReservadasPorPrestador(0, 50)
      .pipe(finalize(() => this.loading.set(false)))
      .subscribe({
        next: (page) => this.disponibilidades.set(page.content),
        error: () => this.error.set('Erro ao carregar reservas.'),
      });
  }
}
