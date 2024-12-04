import { Component, Input } from '@angular/core';
import { Property } from '../../models/property.model';
import { CommonModule, CurrencyPipe } from '@angular/common';

@Component({
  selector: 'app-property-card',
  standalone: true,
  imports: [CurrencyPipe, CommonModule],
  templateUrl: './property-card.component.html',
  styleUrl: './property-card.component.css',
})
export class PropertyCardComponent {
  @Input() property?: Property;
}
