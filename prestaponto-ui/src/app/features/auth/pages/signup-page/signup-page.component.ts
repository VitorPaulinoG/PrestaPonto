import { Component, inject } from '@angular/core';
import { FormControl, FormGroup, NonNullableFormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthShellComponent } from '../../components/auth-shell/auth-shell.component';
import { AUTH_ROLE_OPTIONS, AuthRole } from '../../models/auth.models';
import { Button } from "../../../../shared/components/button/button";
import { TextField } from "../../../../shared/components/text-field/text-field";
import { SelectField } from "../../../../shared/components/select-field/select-field";

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
