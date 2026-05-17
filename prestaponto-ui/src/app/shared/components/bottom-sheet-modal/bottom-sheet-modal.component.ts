import {
  Component,
  ElementRef,
  EventEmitter,
  HostListener,
  inject,
  input,
  Output,
  signal,
} from '@angular/core';

export interface BottomSheetOption {
  value: string;
  label: string;
}

@Component({
  selector: 'app-bottom-sheet-modal',
  imports: [],
  templateUrl: './bottom-sheet-modal.component.html',
  styleUrl: './bottom-sheet-modal.component.scss',
})
export class BottomSheetModalComponent {
  readonly title = input.required<string>();
  readonly options = input.required<BottomSheetOption[]>();
  readonly selectedValue = input<string | null>(null);

  @Output() selected = new EventEmitter<string>();
  @Output() closed = new EventEmitter<void>();

  protected readonly isOpen = signal(false);
  protected readonly isVisible = signal(false);
  private readonly el = inject<ElementRef<HTMLElement>>(ElementRef);

  open(): void {
    this.isOpen.set(true);
    requestAnimationFrame(() => this.isVisible.set(true));
  }

  close(): void {
    this.isVisible.set(false);
    setTimeout(() => {
      this.isOpen.set(false);
      this.closed.emit();
    }, 300);
  }

  select(value: string): void {
    this.selected.emit(value);
    this.close();
  }

  @HostListener('document:keydown.escape')
  onEscape(): void {
    if (this.isOpen()) {
      this.close();
    }
  }
}
