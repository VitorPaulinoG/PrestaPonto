import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import { environment } from '../../../../environments/environment';

export interface BackendCatalogItem {
  id: string;
  name: string;
  description: string;
  category: string;
  price: number;
  provider: {
    id: string;
    name: string;
  };
}

export interface CatalogItem {
  id: string;
  title: string;
  category: string;
  providerName: string;
  price: string;
  rating: number;
  description: string;
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
export class CatalogService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = `${environment.apiUrl}/catalog`;

  findByFilter(params: {
    providerId?: string;
    category?: string;
    name?: string;
    providerName?: string;
    page?: number;
    size?: number;
  }): Observable<PageResponse<CatalogItem>> {
    let httpParams = new HttpParams();

    if (params.providerId) {
      httpParams = httpParams.set('providerId', params.providerId);
    }
    if (params.category) {
      httpParams = httpParams.set('category', params.category);
    }
    if (params.name) {
      httpParams = httpParams.set('name', params.name);
    }
    if (params.providerName) {
      httpParams = httpParams.set('providerName', params.providerName);
    }
    httpParams = httpParams.set('page', String(params.page ?? 0));
    httpParams = httpParams.set('size', String(params.size ?? 20));
    console.log(httpParams);
    return this.http.get<PageResponse<BackendCatalogItem>>(this.baseUrl, {
      params: httpParams,
    }).pipe(map(this.mapPageResponse));
  }

  findById(id: string): Observable<CatalogItem> {
    return this.http.get<BackendCatalogItem>(`${this.baseUrl}/${id}`).pipe(
      map(this.mapItem),
    );
  }

  private mapPageResponse = (resp: PageResponse<BackendCatalogItem>): PageResponse<CatalogItem> => ({
    ...resp,
    content: resp.content.map(this.mapItem),
  });

  private mapItem = (item: BackendCatalogItem): CatalogItem => ({
    id: item.id,
    title: item.name,
    category: item.category,
    providerName: item.provider?.name ?? '—',
    price: `R$ ${item.price.toFixed(2)}`,
    rating: 4.5,
    description: item.description,
  });
}