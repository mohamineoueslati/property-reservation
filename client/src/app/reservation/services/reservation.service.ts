import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment.development';
import { Observable } from 'rxjs';
import { ReservationsResponse } from '../models/reservations-response.model';
import { ReservationFilter } from '../models/reservation-filter.model';
import { NewReservation } from '../models/new-reservation.model';
import { Reservation } from '../models/reservation.model';
import { HttpParamsBuilder } from '../../util/http-params-builder';

@Injectable({ providedIn: 'root' })
export class ReservationService {
  private readonly URL = `${environment.serverUrl}/reservations`;

  constructor(private http: HttpClient) {}

  public getReservations(
    page: number,
    size: number,
    reservationFilter: ReservationFilter
  ): Observable<ReservationsResponse> {
    const params = new HttpParamsBuilder(page, size, reservationFilter).build();
    return this.http.get<ReservationsResponse>(this.URL, { params: params });
  }

  public makeReservation(reservation: NewReservation) {
    return this.http.post<Reservation>(this.URL, reservation);
  }
}
