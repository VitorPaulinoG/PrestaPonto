import { Component, input } from '@angular/core';
import { RouterLinkActive, RouterLink } from '@angular/router';

export interface TabItem {
  label: string;
  routerLink: string;
}

@Component({
  selector: 'app-tabs',
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './tabs.component.html',
  styleUrl: './tabs.component.scss',
})
export class TabsComponent {
  readonly items = input.required<TabItem[]>();
  readonly ariaLabel = input('Navegação');
}
