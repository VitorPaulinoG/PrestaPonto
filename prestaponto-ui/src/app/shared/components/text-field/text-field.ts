import {
  Component,
  input,
} from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-text-field',
  imports: [ReactiveFormsModule],
  templateUrl: './text-field.html',
})
export class TextField {
  readonly control = input.required<FormControl<string>>();
  readonly placeholder = input.required<string>();
  readonly icon = input.required<string>();
  readonly type = input<'text' | 'email' | 'password'>('text');
  readonly autocomplete = input('off');
  readonly errorMessages = input.required<Record<string, string>>();

  get errorMessage() {
    if (!this.control() || !this.control().errors || !this.control().touched) {
      return null;
    }
    
    const firstErrorKey = Object.keys(this.control().errors ?? {})[0];
    return this.errorMessages()[firstErrorKey] || 'Campo inválido.';
  }
}
