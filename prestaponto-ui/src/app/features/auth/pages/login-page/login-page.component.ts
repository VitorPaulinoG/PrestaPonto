import { Component, inject } from '@angular/core';
import { FormControl, FormGroup, NonNullableFormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';

import { AuthFieldComponent } from '../../components/auth-field/auth-field.component';
import { AuthSelectFieldComponent } from '../../components/auth-select-field/auth-select-field.component';
import { AuthShellComponent } from '../../components/auth-shell/auth-shell.component';
import { AUTH_ROLE_OPTIONS, AuthRole } from '../../models/auth.models';

type LoginFormGroup = FormGroup<{
  role: FormControl<AuthRole>;
  email: FormControl<string>;
  password: FormControl<string>;
}>;

@Component({
  selector: 'app-login-page',
  imports: [ReactiveFormsModule, AuthShellComponent, AuthSelectFieldComponent, AuthFieldComponent],
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
