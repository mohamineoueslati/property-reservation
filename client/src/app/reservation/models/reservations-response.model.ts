import { Reservation } from './reservation.model';

export interface ReservationsResponse {
  reservations: Reservation[];
  totalRecords: number;
  totalMoneySpentOnFlats: number;
  totalMoneySpentOnHotels: number;
  totalMoneySpent: number;
  cityWithMostReservations: string;
}
