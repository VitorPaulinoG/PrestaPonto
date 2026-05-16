import { Component, input } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-text-field',
  imports: [ReactiveFormsModule],
  templateUrl: './text-field.html',
  styleUrl: './text-field.scss',
})
export class TextField {
  readonly control = input.required<FormControl<string>>();
  readonly placeholder = input.required<string>();
  readonly icon = input.required<string>();
  readonly type = input<'text' | 'email' | 'password'>('text');
  readonly autocomplete = input('off');
}
