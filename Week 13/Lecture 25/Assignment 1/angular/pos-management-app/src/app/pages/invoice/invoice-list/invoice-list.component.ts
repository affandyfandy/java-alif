import { Component, ViewEncapsulation } from '@angular/core';
import { AgGridAngular } from '@ag-grid-community/angular';
import { CommonModule } from '@angular/common';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { InvoiceAddComponent } from '../invoice-add/invoice-add.component';
import { ColDef, ModuleRegistry } from '@ag-grid-community/core';
import { InvoiceService } from '../../../services/invoice.service';
import { ClientSideRowModelModule } from '@ag-grid-community/client-side-row-model';
import { Router } from '@angular/router';

import '@ag-grid-community/styles/ag-grid.css';
import '@ag-grid-community/styles/ag-theme-alpine.css';
import { ExportDialogComponent } from '../../../main/components/dialog/export-dialog/export-dialog.component';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { RevenueInputDialogComponent } from '../../../main/components/dialog/revenue-input-dialog/revenue-input-dialog.component';
import { RevenueService } from '../../../services/revenue.service';

@Component({
  selector: 'app-invoice-list',
  standalone: true,
  imports: [CommonModule, AgGridAngular, MatSnackBarModule],
  templateUrl: './invoice-list.component.html',
  styleUrls: ['./invoice-list.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class InvoiceListComponent {
  themeClass = 'ag-theme-alpine';

  colDefs: ColDef[] = [
    { field: 'id', sortable: true, filter: true },
    { field: 'customerName', headerName: 'Customer Name', sortable: true, filter: 'agTextColumnFilter' },
    { field: 'invoiceAmount', headerName: 'Invoice Amount', sortable: true, filter: 'agNumberColumnFilter' },
    { field: 'invoiceDate', headerName: 'Invoice Date', sortable: true, filter: 'agDateColumnFilter' },
    {
      headerName: 'Actions',
      cellRenderer: () => {
        return `
          <button class="btn-edit" data-action="edit">Edit</button>
          <button class="btn-delete" data-action="delete">Delete</button>
          <button class="btn-danger" data-action="pdf">PDF</button>
        `;
      },
      sortable: false,
      filter: false,
      width: 200,
    }
  ];

  defaultColDef: ColDef = {
    flex: 1,
    filter: true,
    sortable: true,
    floatingFilter: true
  };

  public paginationPageSize = 10;
  public paginationPageSizeSelector: number[] | boolean = [10, 25, 50];

  rowData: any[] = [];

  constructor(private invoiceService: InvoiceService, private revenueService: RevenueService, private router: Router, private dialog: MatDialog, private snackBar: MatSnackBar) {
    ModuleRegistry.registerModules([ClientSideRowModelModule]);
  }

  ngOnInit(): void {
    this.loadInvoices();
  }

  loadInvoices(): void {
    this.invoiceService.getInvoices().subscribe(data => {
      this.rowData = data;
    });
  }

  onCellClicked(event: any): void {
    const action = event.event.target.getAttribute('data-action');
    if (action === 'edit') {
      this.editInvoice(event.data);
    } else if (action === 'delete') {
      this.deleteInvoice(event.data);
    } else if (action === 'pdf') {
      this.exportInvoiceToPdf(event.data);
    }
  }

  editInvoice(invoice: any): void {
    this.router.navigate([`/invoice/${invoice.id}`]);
  }

  deleteInvoice(invoice: any): void {
    if (confirm(`Do you want to delete Invoice ID: ${invoice.id}?`)) {
      this.invoiceService.deleteInvoice(invoice.id).subscribe(() => {
        this.snackBar.open('Invoice deleted!', 'Close', {
          duration: 3000,
          horizontalPosition: 'center',
          verticalPosition: 'top'
        });
        this.loadInvoices();
      });
    }
  }

  addInvoice(): void {
    if (this.dialog.openDialogs.length === 0) {
      const dialogRef = this.dialog.open(InvoiceAddComponent, {
        width: '400px',
        panelClass: 'center-dialog-container',
      });

      dialogRef.afterClosed().subscribe((result) => {
        if (result === true) {
          this.loadInvoices();
        }
      });
    }
  }

  openRevenueInputDialog(): void {
    const dialogRef = this.dialog.open(RevenueInputDialogComponent, {
      width: '300px'
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        if (result.date) {
          this.fetchRevenueByDay(result.date);
        } else if (result.year && result.month) {
          this.fetchRevenueByMonth(result.year, result.month);
        } else if (result.year) {
          this.fetchRevenueByYear(result.year);
        }
      }
    });
  }

  fetchRevenueByDay(date: string): void {
    this.revenueService.getRevenueByDay(date).subscribe(revenue => {
      console.log('Revenue by day:', revenue);
      const message = `Revenue for ${date}: $${revenue.totalRevenue}`;

      this.snackBar.open(message, 'Close', {
        duration: 8000,
        horizontalPosition: 'center',
        verticalPosition: 'top'
      });
    });
  }

  fetchRevenueByMonth(year: number, month: number): void {
    this.revenueService.getRevenueByMonth(year, month).subscribe(revenue => {
      console.log('Revenue by month:', revenue);
      const message = `Revenue for ${year}-${month.toString().padStart(2, '0')}: $${revenue.totalRevenue}`;

      this.snackBar.open(message, 'Close', {
        duration: 8000,
        horizontalPosition: 'center',
        verticalPosition: 'top'
      });
    });
  }

  fetchRevenueByYear(year: number): void {
    this.revenueService.getRevenueByYear(year).subscribe(revenue => {
      console.log('Revenue by year:', revenue);
      const message = `Revenue for year ${year}: $${revenue.totalRevenue}`;

      this.snackBar.open(message, 'Close', {
        duration: 8000,
        horizontalPosition: 'center',
        verticalPosition: 'top'
      });
    });
  }

  openExportExcelDialog(): void {
    const dialogRef = this.dialog.open(ExportDialogComponent, { });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.invoiceService.exportInvoiceToExcel(result.customer, result.month, result.year).subscribe((blob) => {
          const url = window.URL.createObjectURL(blob);
          const a = document.createElement('a');
          a.href = url;
          a.download = `invoice${result.customer? '-' + result.customer:''}${result.month? '-' + result.month:''}${result.year? '-' + result.year:''}.xlsx`;
          a.click();

          this.snackBar.open('Exported successfully!', 'Close', {
            duration: 3000,
            horizontalPosition: 'center',
            verticalPosition: 'top'
          });
        },
        (error) => {
          this.snackBar.open('Export failed. Please try again', 'Close', {
            duration: 3000,
            horizontalPosition: 'center',
            verticalPosition: 'top'
          });
        });
      }
    });
  }

  exportInvoiceToPdf(invoice: any): void {
    this.invoiceService.exportInvoiceToPdf(invoice.id).subscribe((blob) => {
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = `invoice-${invoice.id}.pdf`;
      a.click();

      this.snackBar.open('Exported successfully!', 'Close', {
        duration: 3000,
        horizontalPosition: 'center',
        verticalPosition: 'top'
      });
    }, (error) => {
      this.snackBar.open('Export failed. Please try again', 'Close', {
        duration: 3000,
        horizontalPosition: 'center',
        verticalPosition: 'top'
      });
    });
  }
}
