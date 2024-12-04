import { PropertyType } from './property-type.enum';

export interface Property {
  name: string;
  propertyType: PropertyType;
  city: string;
  country: string;
  address: string;
  price: number;
  available: boolean;
}
