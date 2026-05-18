import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import { environment } from '../../../../environments/environment';

export interface Disponibilidade {
  id: string;
  prestadorId: string;
  diaSemana: string;
  horaInicio: string;
  horaFim: string;
  reservada: boolean;
}

export interface PageResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  number: number;
  size: number;
  last: boolean;
  first: boolean;
}

@Injectable({ providedIn: 'root' })
export class DisponibilidadeService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = `${environment.apiUrl}`;

  getDisponibilidades(
    prestadorId: string,
    page?: number,
    size?: number,
  ): Observable<PageResponse<Disponibilidade>> {
    let params = new HttpParams();
    params = params.set('prestadorId', prestadorId);
    if (page !== undefined) params = params.set('page', String(page));
    if (size !== undefined) params = params.set('size', String(size));

    return this.http.get<PageResponse<Disponibilidade>>(
      `${this.baseUrl}/prestadores/disponibilidades`,
      { params },
    );
  }

  reservar(disponibilidadeId: string, catalogItemId: string): Observable<void> {
    return this.http.post<void>(
      `${this.baseUrl}/disponibilidades/${disponibilidadeId}`,
      { id: catalogItemId },
    );
  }

  getReservas(page?: number, size?: number): Observable<PageResponse<Disponibilidade>> {
    let params = new HttpParams();
    if (page !== undefined) params = params.set('page', String(page));
    if (size !== undefined) params = params.set('size', String(size));

    return this.http.get<PageResponse<Disponibilidade>>(
      `${this.baseUrl}/disponibilidades/reservas`,
      { params },
    );
  }

  getReservadasPorPrestador(page?: number, size?: number): Observable<PageResponse<Disponibilidade>> {
    let params = new HttpParams();
    if (page !== undefined) params = params.set('page', String(page));
    if (size !== undefined) params = params.set('size', String(size));

    return this.http.get<PageResponse<Disponibilidade>>(
      `${this.baseUrl}/prestadores/disponibilidades/reservadas`,
      { params },
    );
  }
}
