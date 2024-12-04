import { Routes } from '@angular/router';
import { PropertyListComponent } from './property/components/property-list/property-list.component';
import { ReservationListComponent } from './reservation/components/reservation-list/reservation-list.component';
import { MakeReservationComponent } from './reservation/components/make-reservation/make-reservation.component';

export const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'properties' },
  {
    path: 'properties',
    loadComponent: () =>
      import(
        './property/components/property-list/property-list.component'
      ).then((m) => m.PropertyListComponent),
  },
  {
    path: 'reservations',
    loadComponent: () =>
      import(
        './reservation/components/reservation-list/reservation-list.component'
      ).then((m) => m.ReservationListComponent),
  },
  {
    path: 'make-reservation',
    loadComponent: () =>
      import(
        './reservation/components/make-reservation/make-reservation.component'
      ).then((m) => m.MakeReservationComponent),
  },
];
