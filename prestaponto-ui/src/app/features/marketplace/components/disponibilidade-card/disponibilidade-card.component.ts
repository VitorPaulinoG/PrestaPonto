import { Component, input, output } from '@angular/core';

export interface Disponibilidade {
  id: string;
  prestadorId: string;
  diaSemana: string;
  horaInicio: string;
  horaFim: string;
  reservada: boolean;
}

@Component({
  selector: 'app-disponibilidade-card',
  templateUrl: './disponibilidade-card.component.html',
  styleUrl: './disponibilidade-card.component.scss',
})
export class DisponibilidadeCardComponent {
  readonly id = input.required<string>();
  readonly diaSemana = input.required<string>();
  readonly horaInicio = input.required<string>();
  readonly horaFim = input.required<string>();
  readonly reservada = input.required<boolean>();
  readonly click = output<string>();

  protected formatDate(dateStr: string): string {
    const date = new Date(dateStr + 'T00:00:00');
    return date.toLocaleDateString('pt-BR', {
      weekday: 'long',
      day: '2-digit',
      month: '2-digit',
      year: 'numeric',
    });
  }
}
