import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AgGridAngular } from '@ag-grid-community/angular';
import { ClientSideRowModelModule } from '@ag-grid-community/client-side-row-model';
import { ColDef, ModuleRegistry } from '@ag-grid-community/core';
import { CustomerService } from '../../../services/customer.service';
import { Router } from '@angular/router';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { getCurrentTimestamp } from '../../../core/util/date-time.util';
import { CustomerAddComponent } from '../customer-add/customer-add.component';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';

import '@ag-grid-community/styles/ag-grid.css';
import '@ag-grid-community/styles/ag-theme-alpine.css';

@Component({
  selector: 'app-customer-list',
  standalone: true,
  imports: [CommonModule, AgGridAngular, MatDialogModule, MatSnackBarModule],
  templateUrl: './customer-list.component.html',
  styleUrl: './customer-list.component.scss',
  encapsulation: ViewEncapsulation.None
})
export class CustomerListComponent implements OnInit {
  themeClass =  'ag-theme-alpine';

  colDefs: ColDef[] = [
    { field: 'id', sortable: true, filter: true },
    { field: 'name', sortable: true, filter: 'agTextColumnFilter' },
    { field: 'phoneNumber', sortable: true, filter: 'agTextColumnFilter' },
    {
      field: 'status',
      sortable: true,
      filter: 'agTextColumnFilter',
      filterParams: {
        textCustomComparator: (filter: string, value: string, filterText: string) => {
          return value === filterText;
        }
      },
      cellRenderer: (params: any) => {
        const isActive = params.value === 'ACTIVE';
        const switchId = `statusSwitch-${params.node.id}`;

        return `<div class="form-switch row align-items-center">
            <input class="form-check-input col flex-grow-0" type="checkbox" role="switch" id="${switchId}" ${isActive ? 'checked' : ''}>
            <label class="form-check-label col" for="${switchId}">${params.value}</label>
          </div>`;
      },
      cellRendererParams: {
        onStatusChange: (customer: any) => {
          customer.status = customer.status === 'ACTIVE' ? 'INACTIVE' : 'ACTIVE';
        }
      }
     },
    {
      headerName: 'Actions',
      cellRenderer: (params: any) => {
        return `
          <button class="btn-edit" data-action="edit">Edit</button>
          <button class="btn-delete" data-action="delete">Delete</button>
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

  constructor(private customerService: CustomerService, private router: Router, private dialog: MatDialog, private snackBar: MatSnackBar) {
    ModuleRegistry.registerModules([ClientSideRowModelModule]);
  }

  ngOnInit(): void {
      this.loadCustomers();
  }

  loadCustomers(): void {
    this.customerService.getAll().subscribe(data => {
      this.rowData = data;
    });
  }

  onCellClicked(event: any): void {
    const action = event.event.target.getAttribute('data-action');
    if (action === 'edit') {
      this.editCustomer(event.data);
    } else if (action === 'delete') {
      this.deleteCustomer(event.data);
    } else if (event.colDef.field === 'status') {
      this.toggleCustomerStatus(event.data);
    }
  }

  editCustomer(customer: any): void {
    this.router.navigate([`/customer/${customer.id}`]);
  }

  toggleCustomerStatus(customer: any): void {
    const updatedCustomer = {
      ...customer,
      status: customer.status === 'ACTIVE' ? 'INACTIVE' : 'ACTIVE',
      updatedTime: getCurrentTimestamp()};
    this.customerService.changeStatus(customer.id, updatedCustomer).subscribe(() => {
      this.snackBar.open('Status changed successfully', 'Close', {
        duration: 3000,
        horizontalPosition: 'center',
        verticalPosition: 'top'
      });
      this.loadCustomers();
    });
  }

  deleteCustomer(customer: any): void {
    if (confirm(`Do you want to delete ${customer.name}?`)) {
      this.customerService.delete(customer.id).subscribe(() => {
        this.snackBar.open('Customer deleted!', 'Close', {
          duration: 3000,
          horizontalPosition: 'center',
          verticalPosition: 'top'
        });
        this.loadCustomers();
      });
    }
  }

  addCustomer(): void {
    if (this.dialog.openDialogs.length === 0) {
      const dialogRef = this.dialog.open(CustomerAddComponent, {
        width: '400px',
        panelClass: 'center-dialog-container',
      });

      dialogRef.afterClosed().subscribe((result) => {
        if (result === true) {
          this.loadCustomers();
        }
      });
    }
  }
}
