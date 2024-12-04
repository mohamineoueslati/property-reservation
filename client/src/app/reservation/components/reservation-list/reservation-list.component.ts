import { Component, OnInit } from '@angular/core';
import { ReservationFilter } from '../../models/reservation-filter.model';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { PropertyType } from '../../../property/models/property-type.enum';
import { ReservationCardComponent } from '../reservation-card/reservation-card.component';
import { Reservation } from '../../models/reservation.model';
import { ReservationService } from '../../services/reservation.service';
import { MessageComponent } from '../../../shared/message/message.component';

@Component({
  selector: 'app-reservation-list',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ReservationCardComponent,
    MessageComponent,
  ],
  templateUrl: './reservation-list.component.html',
  styleUrl: './reservation-list.component.css',
})
export class ReservationListComponent implements OnInit {
  readonly propertyTypes = [
    PropertyType.HOTEL_ROOM.toString(),
    PropertyType.FLAT.toString(),
  ];
  reservationFilter: ReservationFilter = {};
  reservations: Reservation[] = [];
  totalMoneySpentOnFlat = 0;
  totalMoneySpentOnHotels = 0;
  totalMoneySpent = 0;
  cityWithMostReservations = '';
  totalRecords = 0;
  currentPage: number = 0;
  pageSize: number = 12;
  displayMessage = false;
  message = '';
  messageSeverity: 'success' | 'failure' = 'success';

  constructor(private reservationService: ReservationService) {}

  ngOnInit(): void {
    this.getReservations();
  }

  getReservations() {
    this.reservationService
      .getReservations(this.currentPage, this.pageSize, this.reservationFilter)
      .subscribe({
        next: (res) => {
          this.reservations = res.reservations;
          this.totalRecords = res.totalRecords;
          this.totalMoneySpentOnFlat = res.totalMoneySpentOnFlats;
          this.totalMoneySpentOnHotels = res.totalMoneySpentOnHotels;
          this.totalMoneySpent = res.totalMoneySpent;
          this.cityWithMostReservations = res.cityWithMostReservations;
        },
        error: (error) => {
          this.message = error;
          this.messageSeverity = 'failure';
          this.displayMessage = true;
        },
      });
  }

  onFilter(filterForm: NgForm) {
    if (filterForm.valid) {
      const fromDate = filterForm.value.fromDate
        ? new Date(filterForm.value.fromDate).toISOString()
        : '';
      const toDate = filterForm.value.toDate
        ? new Date(filterForm.value.toDate).toISOString()
        : '';
      this.reservationFilter = {
        fromDate: fromDate,
        toDate: toDate,
        searchQuery: filterForm.value.searchQuery,
        propertyType: filterForm.value.propertyType,
        minPrice: filterForm.value.minPrice,
        maxPrice: filterForm.value.maxPrice,
      };
      this.getReservations();
    }
  }

  nextPage() {
    if ((this.currentPage + 1) * this.pageSize < this.totalRecords) {
      this.currentPage++;
      this.getReservations();
    }
  }

  prevPage() {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.getReservations();
    }
  }
}
