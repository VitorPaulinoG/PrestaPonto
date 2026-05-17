import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class TokenService {
  private readonly KEY = 'pp_token';

  getToken(): string | null {
    return localStorage.getItem(this.KEY);
  }

  setToken(token: string): void {
    localStorage.setItem(this.KEY, token);
  }

  removeToken(): void {
    localStorage.removeItem(this.KEY);
  }

  hasToken(): boolean {
    return !!this.getToken();
  }

  getRole(): string | null {
    const token = this.getToken();
    if (!token) return null;

    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      return payload.role ?? null;
    } catch {
      return null;
    }
  }
}
