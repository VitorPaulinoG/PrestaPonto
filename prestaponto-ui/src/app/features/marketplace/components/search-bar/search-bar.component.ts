import { Component, input, output, ViewChild, ElementRef } from '@angular/core';

@Component({
  selector: 'app-search-bar',
  imports: [],
  templateUrl: './search-bar.component.html',
  styleUrl: './search-bar.component.scss',
})
export class SearchBarComponent {
  readonly placeholder = input.required<string>();
  readonly value = input<string>('');
  readonly search = output<string>();

  @ViewChild('input', { static: true }) inputRef!: ElementRef<HTMLInputElement>;

  protected onKeydown(event: KeyboardEvent): void {
    if (event.key === 'Enter') {
      const val = this.inputRef.nativeElement.value.trim();
      if (val) {
        this.search.emit(val);
      }
    }
  }
}
