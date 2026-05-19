import { Component, input, output, ViewChild } from '@angular/core';

import {
  BottomSheetModalComponent,
  BottomSheetOption,
} from '../bottom-sheet-modal/bottom-sheet-modal.component';

@Component({
  selector: 'app-filter-dropdown',
  imports: [BottomSheetModalComponent],
  templateUrl: './filter-dropdown.html',
  styleUrl: './filter-dropdown.scss',
})
export class FilterDropdown {
  title = input.required<string>();
  modalTitle = input<string>();
  selectedOption = input.required<string>();
  isSelected = input.required<boolean>();
  options = input.required<BottomSheetOption[]>();

  optionSelected = output<string>();

  @ViewChild(BottomSheetModalComponent) protected modal!: BottomSheetModalComponent;

  protected onOptionSelected(value: string): void {
    this.optionSelected.emit(value);
  }
}
