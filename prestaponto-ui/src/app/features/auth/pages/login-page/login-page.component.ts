import { Component, inject } from '@angular/core';
import { FormControl, FormGroup, NonNullableFormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';

import { AuthShellComponent } from '../../components/auth-shell/auth-shell.component';
import { Button } from "../../../../shared/components/button/button";
import { TextField } from "../../../../shared/components/text-field/text-field";

type LoginFormGroup = FormGroup<{
  email: FormControl<string>;
  password: FormControl<string>;
}>;

@Component({
  selector: 'app-login-page',
  imports: [ReactiveFormsModule, AuthShellComponent, Button, TextField],
  templateUrl: './login-page.component.html',
  styleUrl: './login-page.component.scss',
})
export class LoginPageComponent {
  private readonly formBuilder = inject(NonNullableFormBuilder);
  
  protected readonly emailValidationMessages: Record<string, string> = {
    required: 'Campo obrigatório.',
    email: 'Email inválido.',
  };
  protected readonly passwordValidationMessages: Record<string, string> = {
    required: 'Campo obrigatório.',
  };

  protected readonly form: LoginFormGroup = this.formBuilder.group({
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
