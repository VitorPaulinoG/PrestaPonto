import { Component, input } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';

import { AuthRoleOption } from '../../models/auth.models';

@Component({
  selector: 'app-auth-select-field',
  imports: [ReactiveFormsModule],
  templateUrl: './auth-select-field.component.html',
  styleUrl: './auth-select-field.component.scss',
})
export class AuthSelectFieldComponent {
  readonly control = input.required<FormControl<string>>();
  readonly options = input.required<AuthRoleOption[]>();
}
