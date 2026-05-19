import { Component, inject, signal, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { AppMobileShellComponent } from '../../components/app-mobile-shell/app-mobile-shell.component';
import { BasicHeaderComponent } from '../../../../shared/components/basic-header/basic-header.component';
import { PageContentComponent } from '../../../../shared/components/page-content/page-content.component';
import { SectionBlockComponent } from '../../../../shared/components/section-block/section-block.component';
import { DisponibilidadeCardComponent } from '../../components/disponibilidade-card/disponibilidade-card.component';
import { Disponibilidade, DisponibilidadeService } from '../../services/disponibilidade.service';
import { CLIENT_NAV_ITEMS } from '../../models/marketplace.models';

@Component({
  selector: 'app-disponibilidade-page',
  imports: [
    AppMobileShellComponent,
    BasicHeaderComponent,
    PageContentComponent,
    SectionBlockComponent,
    DisponibilidadeCardComponent,
  ],
  templateUrl: './disponibilidade-page.component.html',
  styleUrl: './disponibilidade-page.component.scss',
})
export class DisponibilidadePageComponent implements OnInit {
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);
  private readonly disponibilidadeService = inject(DisponibilidadeService);

  protected readonly navItems = CLIENT_NAV_ITEMS;
  protected readonly prestadorId = signal<string>('');
  protected readonly catalogItemId = signal<string>('');
  protected readonly disponibilidades = signal<Disponibilidade[]>([]);
  protected readonly loading = signal(false);
  protected readonly successMessage = signal<string>('');

  protected readonly selectedSlot = signal<Disponibilidade | null>(null);
  protected readonly sheetOpen = signal(false);
  protected readonly sheetVisible = signal(false);
  protected readonly reservando = signal(false);

  ngOnInit(): void {
    const params = this.route.snapshot.params;
    const query = this.route.snapshot.queryParams;
    this.prestadorId.set(params['prestadorId'] ?? '');
    this.catalogItemId.set(query['catalogItemId'] ?? '');
    this.loadDisponibilidades();
  }

  private loadDisponibilidades(): void {
    this.loading.set(true);
    this.disponibilidadeService
      .getDisponibilidades(this.prestadorId())
      .subscribe({
        next: (resp) => {
          this.disponibilidades.set(resp.content);
          this.loading.set(false);
        },
        error: () => {
          this.loading.set(false);
        },
      });
  }

  protected onCardClick(id: string): void {
    const slot = this.disponibilidades().find((d) => d.id === id);
    if (!slot || slot.reservada) return;
    this.selectedSlot.set(slot);
    this.sheetOpen.set(true);
    requestAnimationFrame(() => this.sheetVisible.set(true));
  }

  protected closeSheet(): void {
    this.sheetVisible.set(false);
    setTimeout(() => {
      this.sheetOpen.set(false);
      this.selectedSlot.set(null);
    }, 300);
  }

  protected onReservar(): void {
    const slot = this.selectedSlot();
    const catalogId = this.catalogItemId();
    if (!slot || !catalogId) return;

    this.reservando.set(true);
    this.disponibilidadeService.reservar(slot.id, catalogId).subscribe({
      next: () => {
        this.reservando.set(false);
        this.closeSheet();
        this.successMessage.set('Serviço reservado com sucesso!');
        this.loadDisponibilidades();
        setTimeout(() => this.successMessage.set(''), 3000);
      },
      error: () => {
        this.reservando.set(false);
      },
    });
  }

  protected onBack(): void {
    this.router.navigate(['/catalog-item', this.catalogItemId()]);
  }
}
