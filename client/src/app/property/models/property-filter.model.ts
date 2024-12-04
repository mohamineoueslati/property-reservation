export interface PropertyFilter {
  searchQuery?: string;
  propertyType?: string;
  minPrice?: number;
  maxPrice?: number;
  fromDate: string;
  toDate: string;
  available?: boolean;
}
