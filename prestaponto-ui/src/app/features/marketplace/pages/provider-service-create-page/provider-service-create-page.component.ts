import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { FormControl, FormGroup, NonNullableFormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';

import { AppMobileShellComponent } from '../../components/app-mobile-shell/app-mobile-shell.component';
import { LabeledInputComponent } from '../../components/labeled-input/labeled-input.component';
import { BasicHeaderComponent } from '../../../../shared/components/basic-header/basic-header.component';
import { CatalogService } from '../../services/catalog.service';
import { PROVIDER_NAV_ITEMS } from '../../models/marketplace.models';

type ServiceFormGroup = FormGroup<{
  name: FormControl<string>;
  description: FormControl<string>;
  category: FormControl<string>;
  price: FormControl<string>;
}>;

@Component({
  selector: 'app-provider-service-create-page',
  imports: [ReactiveFormsModule, AppMobileShellComponent, BasicHeaderComponent, LabeledInputComponent],
  templateUrl: './provider-service-create-page.component.html',
})
export class ProviderServiceCreatePageComponent {
  private readonly formBuilder = inject(NonNullableFormBuilder);
  private readonly catalogService = inject(CatalogService);
  private readonly router = inject(Router);

  protected readonly navItems = PROVIDER_NAV_ITEMS;
  protected readonly form: ServiceFormGroup = this.formBuilder.group({
    name: this.formBuilder.control('', [Validators.required, Validators.minLength(3)]),
    description: this.formBuilder.control('', [Validators.required, Validators.maxLength(1000)]),
    category: this.formBuilder.control('', [Validators.required]),
    price: this.formBuilder.control('', [Validators.required]),
  });

  protected onBack(): void {
    this.router.navigate(['/provider/catalog']);
  }

  protected submit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const raw = this.form.getRawValue();
    const price = Number(raw.price.replace(',', '.'));

    this.catalogService.register({
      name: raw.name,
      description: raw.description,
      category: raw.category,
      price,
    }).subscribe({
      next: () => this.router.navigate(['/provider/catalog']),
    });
  }
}
