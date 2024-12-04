import { HttpParams } from '@angular/common/http';

export class HttpParamsBuilder {
  private params = new HttpParams();

  constructor(
    private page: number,
    private pageSize: number,
    private filter: any
  ) {}

  build(): HttpParams {
    this.params = this.params
      .append('page', this.page.toString())
      .append('size', this.pageSize.toString());

    this.addFilterParams();

    return this.params;
  }

  private addFilterParams(): void {
    for (const key in this.filter) {
      if (this.filter[key] !== null && this.filter[key] !== undefined) {
        this.params = this.params.append(key, this.filter[key]);
      }
    }
  }
}
