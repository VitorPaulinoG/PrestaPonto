import { Component, inject } from '@angular/core';
import { FormControl, FormGroup, NonNullableFormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';

import { AuthShellComponent } from '../../components/auth-shell/auth-shell.component';
import { AUTH_ROLE_OPTIONS, AuthRole } from '../../models/auth.models';
import { Button } from "../../../../shared/components/button/button";
import { TextField } from "../../../../shared/components/text-field/text-field";
import { SelectField } from "../../../../shared/components/select-field/select-field";

type LoginFormGroup = FormGroup<{
  role: FormControl<AuthRole>;
  email: FormControl<string>;
  password: FormControl<string>;
}>;

@Component({
  selector: 'app-login-page',
  imports: [ReactiveFormsModule, AuthShellComponent, Button, TextField, SelectField],
  templateUrl: './login-page.component.html',
  styleUrl: './login-page.component.scss',
})
export class LoginPageComponent {
  private readonly formBuilder = inject(NonNullableFormBuilder);

  protected readonly roleOptions = AUTH_ROLE_OPTIONS;
  protected readonly form: LoginFormGroup = this.formBuilder.group({
    role: this.formBuilder.control<AuthRole>('PROVIDER', Validators.required),
    email: this.formBuilder.control('', [Validators.required, Validators.email]),
    password: this.formBuilder.control('', [Validators.required]),
  });

  protected submit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    console.info('Login form ready for API integration', this.form.getRawValue());
  }
}
