import { Component, inject, signal } from '@angular/core';
import { FormControl, FormGroup, NonNullableFormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthShellComponent } from '../../components/auth-shell/auth-shell.component';
import { Button } from "../../../../shared/components/button/button";
import { TextField } from "../../../../shared/components/text-field/text-field";
import { AuthService } from '../../../../core/services/auth.service';
import { TokenService } from '../../../../core/services/token.service';
import { Router } from '@angular/router';
import { extractErrorMessage } from '../../../../core/utils/api-error.utils';

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
  private readonly authService = inject(AuthService);
  private readonly tokenService = inject(TokenService);
  private readonly router = inject(Router);

  protected readonly emailValidationMessages: Record<string, string> = {
    required: 'Campo obrigatorio.',
    email: 'Email invalido.',
  };
  protected readonly passwordValidationMessages: Record<string, string> = {
    required: 'Campo obrigatorio.',
  };

  protected readonly loading = signal(false);

  protected readonly form: LoginFormGroup = this.formBuilder.group({
    email: this.formBuilder.control('', [Validators.required, Validators.email]),
    password: this.formBuilder.control('', [Validators.required]),
  });

  protected submit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.loading.set(true);

    this.authService.login(this.form.getRawValue()).subscribe({
      next: (response) => {
        this.tokenService.setToken(response.token);
        console.log(response.token);
        this.router.navigate(['/home']);
      },
      error: (err) => {
        this.loading.set(false);
        console.log(extractErrorMessage(err));
      },
    });
  }
}
