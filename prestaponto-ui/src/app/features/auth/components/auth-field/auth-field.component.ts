import { Component, input } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';

type AuthFieldIcon = 'email' | 'password' | 'person';

@Component({
  selector: 'app-auth-field',
  imports: [ReactiveFormsModule],
  templateUrl: './auth-field.component.html',
  styleUrl: './auth-field.component.scss',
})
export class AuthFieldComponent {
  readonly control = input.required<FormControl<string>>();
  readonly placeholder = input.required<string>();
  readonly icon = input.required<AuthFieldIcon>();
  readonly type = input<'text' | 'email' | 'password'>('text');
  readonly autocomplete = input('off');
}
