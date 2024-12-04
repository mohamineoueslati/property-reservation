import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { PropertyFilter } from '../../models/property-filter.model';
import { PropertyType } from '../../models/property-type.enum';
import { PropertyCardComponent } from '../property-card/property-card.component';
import { Property } from '../../models/property.model';
import { PropertyService } from '../../services/property.service';
import { MessageComponent } from '../../../shared/message/message.component';

@Component({
  selector: 'app-property-list',
  standalone: true,
  imports: [CommonModule, FormsModule, PropertyCardComponent, MessageComponent],
  templateUrl: './property-list.component.html',
  styleUrl: './property-list.component.css',
})
export class PropertyListComponent implements OnInit {
  readonly today: Date;
  readonly tomorrow: Date;
  readonly propertyTypes = [
    PropertyType.HOTEL_ROOM.toString(),
    PropertyType.FLAT.toString(),
  ];
  propertyFilter: PropertyFilter;
  properties: Property[] = [];
  totalRecords = 0;
  currentPage: number = 0;
  pageSize: number = 12;
  displayMessage = false;
  message = '';
  messageSeverity: 'success' | 'failure' = 'success';

  constructor(private propertyService: PropertyService) {
    this.today = new Date();
    this.tomorrow = new Date();
    this.tomorrow.setDate(this.today.getDate() + 1);
    this.propertyFilter = {
      fromDate: this.today.toISOString(),
      toDate: this.tomorrow.toISOString(),
    };
  }

  ngOnInit(): void {
    this.getProperties();
  }

  getProperties() {
    this.propertyService
      .getProperties(this.currentPage, this.pageSize, this.propertyFilter)
      .subscribe({
        next: (res) => {
          this.properties = res.properties;
          this.totalRecords = res.totalRecords;
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
      this.propertyFilter = {
        fromDate: new Date(filterForm.value.fromDate).toISOString(),
        toDate: new Date(filterForm.value.toDate).toISOString(),
        available: filterForm.value.availability,
        propertyType: filterForm.value.propertyType,
        searchQuery: filterForm.value.searchQuery,
        minPrice: filterForm.value.minPrice,
        maxPrice: filterForm.value.maxPrice,
      };
      this.getProperties();
    }
  }

  nextPage() {
    if ((this.currentPage + 1) * this.pageSize < this.totalRecords) {
      this.currentPage++;
      this.getProperties();
    }
  }

  prevPage() {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.getProperties();
    }
  }
}
