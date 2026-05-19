import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { FormControl, FormGroup, NonNullableFormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';

import { AppMobileShellComponent } from '../../components/app-mobile-shell/app-mobile-shell.component';
import { BasicHeaderComponent } from '../../../../shared/components/basic-header/basic-header.component';
import { LabeledInputComponent } from '../../components/labeled-input/labeled-input.component';
import { DisponibilidadeService } from '../../services/disponibilidade.service';
import { PROVIDER_NAV_ITEMS } from '../../models/marketplace.models';

type AgendaFormGroup = FormGroup<{
  diaSemana: FormControl<string>;
  horaInicio: FormControl<string>;
  horaFim: FormControl<string>;
}>;

@Component({
  selector: 'app-provider-agenda-create-page',
  imports: [ReactiveFormsModule, AppMobileShellComponent, BasicHeaderComponent, LabeledInputComponent],
  templateUrl: './provider-agenda-create-page.component.html',
})
export class ProviderAgendaCreatePageComponent {
  private readonly formBuilder = inject(NonNullableFormBuilder);
  private readonly disponibilidadeService = inject(DisponibilidadeService);
  private readonly router = inject(Router);

  protected readonly navItems = PROVIDER_NAV_ITEMS;
  protected readonly form: AgendaFormGroup = this.formBuilder.group({
    diaSemana: this.formBuilder.control('', [Validators.required]),
    horaInicio: this.formBuilder.control('', [Validators.required]),
    horaFim: this.formBuilder.control('', [Validators.required]),
  });

  protected onBack(): void {
    this.router.navigate(['/provider/agenda']);
  }

  protected submit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const raw = this.form.getRawValue();
    this.disponibilidadeService.cadastrar(raw).subscribe({
      next: () => this.router.navigate(['/provider/agenda']),
    });
  }
}
