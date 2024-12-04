import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment.development';
import { PropertyFilter } from '../models/property-filter.model';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PropertiesResponse } from '../models/properties-response.model';
import { HttpParamsBuilder } from '../../util/http-params-builder';

@Injectable({ providedIn: 'root' })
export class PropertyService {
  private readonly URL = `${environment.serverUrl}/properties`;

  constructor(private http: HttpClient) {}

  public getProperties(
    page: number,
    size: number,
    propertyFilter: PropertyFilter
  ): Observable<PropertiesResponse> {
    const params = new HttpParamsBuilder(page, size, propertyFilter).build();
    return this.http.get<PropertiesResponse>(this.URL, { params: params });
  }
}
