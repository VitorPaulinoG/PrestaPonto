import { Component, input } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-page-topbar',
  imports: [RouterLink],
  templateUrl: './page-topbar.component.html',
  styleUrl: './page-topbar.component.scss',
})
export class PageTopbarComponent {
  readonly title = input.required<string>();
  readonly backRoute = input<string | null>(null);
}
