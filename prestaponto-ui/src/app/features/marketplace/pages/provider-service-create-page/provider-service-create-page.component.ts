import { Component, inject } from '@angular/core';
import { FormControl, FormGroup, NonNullableFormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';

import { AppMobileShellComponent } from '../../components/app-mobile-shell/app-mobile-shell.component';
import { LabeledInputComponent } from '../../components/labeled-input/labeled-input.component';
import { PageTopbarComponent } from '../../components/page-topbar/page-topbar.component';
import { PROVIDER_NAV_ITEMS } from '../../models/marketplace.models';

type ServiceFormGroup = FormGroup<{
  name: FormControl<string>;
  description: FormControl<string>;
  category: FormControl<string>;
  price: FormControl<string>;
}>;

@Component({
  selector: 'app-provider-service-create-page',
  imports: [ReactiveFormsModule, AppMobileShellComponent, PageTopbarComponent, LabeledInputComponent],
  templateUrl: './provider-service-create-page.component.html',
  styleUrl: './provider-service-create-page.component.scss',
})
export class ProviderServiceCreatePageComponent {
  private readonly formBuilder = inject(NonNullableFormBuilder);

  protected readonly navItems = PROVIDER_NAV_ITEMS;
  protected readonly form: ServiceFormGroup = this.formBuilder.group({
    name: this.formBuilder.control('', [Validators.required, Validators.minLength(3)]),
    description: this.formBuilder.control('', [Validators.required, Validators.maxLength(1000)]),
    category: this.formBuilder.control('', [Validators.required]),
    price: this.formBuilder.control('', [Validators.required]),
  });

  protected submit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    console.info('Catalog item form ready for API integration', this.form.getRawValue());
  }
}
