import { CommonModule, NgFor, NgIf } from '@angular/common';
import { Component, OnInit, ViewChild } from '@angular/core';
import { InvoiceEditComponent } from '../invoice-edit/invoice-edit.component';
import { ActivatedRoute, Router } from '@angular/router';
import { InvoiceService } from '../../../services/invoice.service';
import { AgGridAngular } from '@ag-grid-community/angular';
import { ColDef, ModuleRegistry } from '@ag-grid-community/core';

import '@ag-grid-community/styles/ag-grid.css';
import '@ag-grid-community/styles/ag-theme-alpine.css';
import { ClientSideRowModelModule } from '@ag-grid-community/client-side-row-model';

@Component({
  selector: 'app-invoice-detail',
  standalone: true,
  imports: [NgIf, InvoiceEditComponent, NgFor, AgGridAngular, CommonModule],
  templateUrl: './invoice-detail.component.html',
  styleUrl: './invoice-detail.component.scss'
})
export class InvoiceDetailComponent implements OnInit{
  invoice: any;
  selectedProductId: number | null = null;

  themeClass = 'ag-theme-alpine';

  colDefs: ColDef[] = [
    { field: 'productId', headerName: 'Product ID', sortable: true, filter: true },
    { field: 'productName', headerName: 'Product Name', sortable: true, filter: 'agTextColumnFilter' },
    { field: 'quantity', headerName: 'Quantity', sortable: true, filter: 'agNumberColumnFilter' },
    { field: 'price', headerName: 'Price', sortable: true, filter: 'agNumberColumnFilter' },
    { field: 'amount', headerName: 'Amount', sortable: true, filter: 'agNumberColumnFilter' }
  ];

  defaultColDef: ColDef = {
    flex: 1,
    filter: true,
    sortable: true,
    floatingFilter: true
  };

  rowData: any[] = [];

  public paginationPageSize = 10;
  public paginationPageSizeSelector: number[] | boolean = [10, 25, 50];

  @ViewChild('agGrid') agGrid!: AgGridAngular;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private invoiceService: InvoiceService
  ) {
    ModuleRegistry.registerModules([ClientSideRowModelModule]);
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      const invoiceId = params['id'];
      this.loadInvoice(invoiceId);
    });
  }

  loadInvoice(invoiceId: string): void {
    this.invoiceService.get(invoiceId).subscribe(
      data => {
        this.invoice = data;
        this.rowData = this.invoice.products;
      }
    )
  }

  onInvoiceUpdated(updatedInvoice: any): void {
    this.invoice = updatedInvoice;
    this.rowData = [...updatedInvoice.products];
    if (this.agGrid && this.agGrid.api) {
      this.agGrid.api.refreshCells({ force: true}); // Update AG Grid
    }
  }

  goBack(): void {
    this.router.navigate(['/invoice']);
  }

  onCellClicked(event: any): void {
    this.selectedProductId = event.data.productId;
  }
}
