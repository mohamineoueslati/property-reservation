import { Component, Input } from '@angular/core';
import { Reservation } from '../../models/reservation.model';
import { CommonModule, CurrencyPipe, DatePipe } from '@angular/common';

@Component({
  selector: 'app-reservation-card',
  standalone: true,
  imports: [CommonModule, CurrencyPipe],
  templateUrl: './reservation-card.component.html',
  styleUrl: './reservation-card.component.css',
})
export class ReservationCardComponent {
  @Input() reservation?: Reservation;
}
