import { Component, input } from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';

let nextInputId = 0;

@Component({
  selector: 'app-labeled-input',
  imports: [ReactiveFormsModule],
  templateUrl: './labeled-input.component.html',
  styleUrl: './labeled-input.component.scss',
})
export class LabeledInputComponent {
  readonly label = input.required<string>();
  readonly placeholder = input.required<string>();
  readonly control = input.required<FormControl<string>>();
  readonly multiline = input(false);
  readonly rows = input(2);
  readonly inputMode = input<'text' | 'decimal'>('text');
  protected readonly inputId = `labeled-input-${nextInputId++}`;
}
