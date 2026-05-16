import { Component, HostListener, computed, effect, input, signal } from '@angular/core';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-select-field',
  templateUrl: './select-field.html',
  styleUrl: './select-field.scss',
})
export class SelectField {
  readonly control = input.required<FormControl<string>>();
  readonly options = input.required<SelectFieldOption[]>();
  readonly isOpen = signal(false);
  readonly selectedValue = signal('');
  readonly selectedLabel = computed(() => {
    const selectedOption = this.options().find(
      (option) => option.value === this.selectedValue(),
    );

    return selectedOption?.label ?? this.options()[0]?.label ?? '';
  });

  constructor() {
    effect((onCleanup) => {
      const control = this.control();

      this.selectedValue.set(control.value ?? '');

      const subscription = control.valueChanges.subscribe((value) => {
        this.selectedValue.set(value ?? '');
      });

      onCleanup(() => subscription.unsubscribe());
    });
  }

  toggleDropdown(): void {
    this.isOpen.update((isOpen) => !isOpen);
  }

  selectOption(value: string): void {
    this.control().setValue(value);
    this.selectedValue.set(value);
    this.control().markAsDirty();
    this.control().markAsTouched();
    this.isOpen.set(false);
  }

  handleBlur(event: FocusEvent): void {
    const nextFocusedElement = event.relatedTarget;

    if (!(nextFocusedElement instanceof HTMLElement) || !nextFocusedElement.closest('.select-field')) {
      this.isOpen.set(false);
      this.control().markAsTouched();
    }
  }

  @HostListener('document:click', ['$event.target'])
  closeOnOutsideClick(target: EventTarget | null): void {
    if (!(target instanceof HTMLElement) || target.closest('.select-field')) {
      return;
    }

    this.isOpen.set(false);
    this.control().markAsTouched();
  }
}

export interface SelectFieldOption {
  label: string;
  value: string;
}
