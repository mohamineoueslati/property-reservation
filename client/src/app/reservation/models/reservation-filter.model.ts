export interface ReservationFilter {
  searchQuery?: string;
  propertyType?: string;
  minPrice?: number;
  maxPrice?: number;
  fromDate?: string;
  toDate?: string;
}
