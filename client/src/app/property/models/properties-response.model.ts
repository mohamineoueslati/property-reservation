import { Property } from './property.model';

export interface PropertiesResponse {
  properties: Property[];
  totalRecords: number;
}
