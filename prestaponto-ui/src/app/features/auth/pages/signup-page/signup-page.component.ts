import { Component, inject, signal } from '@angular/core';
import { FormControl, FormGroup, NonNullableFormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthShellComponent } from '../../components/auth-shell/auth-shell.component';
import { AUTH_ROLE_OPTIONS, AuthRole } from '../../models/auth.models';
import { Button } from "../../../../shared/components/button/button";
import { TextField } from "../../../../shared/components/text-field/text-field";
import { SelectField } from "../../../../shared/components/select-field/select-field";
import { AuthService } from '../../../../core/services/auth.service';
import { Router } from '@angular/router';
import { extractErrorMessage, extractFieldErrors } from '../../../../core/utils/api-error.utils';

type SignupFormGroup = FormGroup<{
  role: FormControl<AuthRole>;
  name: FormControl<string>;
  email: FormControl<string>;
  password: FormControl<string>;
  confirmPassword: FormControl<string>;
}>;

@Component({
  selector: 'app-signup-page',
  imports: [ReactiveFormsModule, AuthShellComponent, Button, TextField, SelectField],
  templateUrl: './signup-page.component.html',
  styleUrl: './signup-page.component.scss',
})
export class SignupPageComponent {
  private readonly formBuilder = inject(NonNullableFormBuilder);
  private readonly authService = inject(AuthService);
  private readonly router = inject(Router);

  protected readonly roleOptions = AUTH_ROLE_OPTIONS;

  protected readonly nameValidationMessages: Record<string, string> = {
    required: 'Campo obrigatorio.',
  };
  protected readonly emailValidationMessages: Record<string, string> = {
    required: 'Campo obrigatorio.',
    email: 'Email invalido.',
  };
  protected readonly passwordValidationMessages: Record<string, string> = {
    required: 'Campo obrigatorio.',
    minlength: 'Minimo 6 caracteres.',
  };
  protected readonly confirmPasswordValidationMessages: Record<string, string> = {
    required: 'Campo obrigatorio.',
  };

  protected readonly loading = signal(false);
  protected serverErrors: Record<string, string> = {};

  protected readonly form: SignupFormGroup = this.formBuilder.group({
    role: this.formBuilder.control<AuthRole>('PROVIDER', Validators.required),
    name: this.formBuilder.control('', [Validators.required]),
    email: this.formBuilder.control('', [Validators.required, Validators.email]),
    password: this.formBuilder.control('', [Validators.required, Validators.minLength(6)]),
    confirmPassword: this.formBuilder.control('', [Validators.required]),
  });

  protected submit(): void {
    if (this.form.invalid || this.passwordsDoNotMatch()) {
      this.form.markAllAsTouched();
      return;
    }

    this.loading.set(true);

    const raw = this.form.getRawValue();
    const payload = {
      name: raw.name,
      email: raw.email,
      password: raw.password,
      userType: raw.role
    };

    this.authService.signup(payload).subscribe({
      next: () => {
        this.router.navigate(['/login']);
      },
      error: (err) => {
        this.loading.set(false);
      },
    });
  }

  private passwordsDoNotMatch(): boolean {
    return this.form.controls.password.value !== this.form.controls.confirmPassword.value;
  }
}
