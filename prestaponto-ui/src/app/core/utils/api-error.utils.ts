import { HttpErrorResponse } from '@angular/common/http';

export interface ProblemDetail {
  type?: string;
  title?: string;
  status?: number;
  detail?: string;
  instance?: string;
  errors?: Record<string, string[]>;
}

export function extractFieldErrors(error: unknown): Record<string, string> | null {
  if (error instanceof HttpErrorResponse) {
    const body = error.error as ProblemDetail | null;
    if (body?.errors) {
      const result: Record<string, string> = {};
      Object.entries(body.errors).forEach(([field, messages]) => {
        if (messages.length > 0) {
          result[field] = messages[0];
        }
      });
      return result;
    }
  }
  return null;
}

export function extractErrorMessage(error: unknown): string {
  if (error instanceof HttpErrorResponse) {
    const body = error.error as ProblemDetail | null;
    return body?.detail || 'Erro de conexao. Tente novamente.';
  }
  if (error instanceof Error) {
    return error.message;
  }
  return 'Erro desconhecido. Tente novamente.';
}
