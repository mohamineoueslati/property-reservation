import { Component } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { PropertyType } from '../../../property/models/property-type.enum';
import { NewReservation } from '../../models/new-reservation.model';
import { CommonModule } from '@angular/common';
import { ReservationService } from '../../services/reservation.service';
import { MessageComponent } from '../../../shared/message/message.component';

@Component({
  selector: 'app-make-reservation',
  standalone: true,
  imports: [CommonModule, FormsModule, MessageComponent],
  templateUrl: './make-reservation.component.html',
  styleUrl: './make-reservation.component.css',
})
export class MakeReservationComponent {
  readonly propertyTypes = [
    PropertyType.HOTEL_ROOM.toString(),
    PropertyType.FLAT.toString(),
  ];
  readonly countries = ['TUN', 'FRA', 'USA'];
  displayMessage = false;
  message = '';
  messageSeverity: 'success' | 'failure' = 'success';

  constructor(private reservationService: ReservationService) {}

  makeReservation(reservationForm: NgForm) {
    if (reservationForm.valid) {
      const reservation: NewReservation = {
        propertyName: reservationForm.value.propertyName,
        propertyType: reservationForm.value.propertyType,
        address: reservationForm.value.address,
        city: reservationForm.value.city,
        country: reservationForm.value.country,
        price: reservationForm.value.price,
        fromDate: new Date().toISOString(),
        toDate: new Date().toISOString(),
      };
      this.reservationService.makeReservation(reservation).subscribe({
        next: (_) => {
          this.displayMessage = true;
          this.message = 'Reservation added successfully';
          this.messageSeverity = 'success';
          reservationForm.resetForm();
        },
        error: (error) => {
          this.message = error;
          this.messageSeverity = 'failure';
          this.displayMessage = true;
        },
      });
    }
  }
}
