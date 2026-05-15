import { Component, inject } from '@angular/core';
import { FormControl, FormGroup, NonNullableFormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';

import { AuthFieldComponent } from '../../components/auth-field/auth-field.component';
import { AuthSelectFieldComponent } from '../../components/auth-select-field/auth-select-field.component';
import { AuthShellComponent } from '../../components/auth-shell/auth-shell.component';
import { AUTH_ROLE_OPTIONS, AuthRole } from '../../models/auth.models';

type SignupFormGroup = FormGroup<{
  role: FormControl<AuthRole>;
  name: FormControl<string>;
  email: FormControl<string>;
  password: FormControl<string>;
  confirmPassword: FormControl<string>;
}>;

@Component({
  selector: 'app-signup-page',
  imports: [ReactiveFormsModule, AuthShellComponent, AuthSelectFieldComponent, AuthFieldComponent],
  templateUrl: './signup-page.component.html',
  styleUrl: './signup-page.component.scss',
})
export class SignupPageComponent {
  private readonly formBuilder = inject(NonNullableFormBuilder);

  protected readonly roleOptions = AUTH_ROLE_OPTIONS;
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

    console.info('Signup form ready for API integration', this.form.getRawValue());
  }

  private passwordsDoNotMatch(): boolean {
    return this.form.controls.password.value !== this.form.controls.confirmPassword.value;
  }
}
