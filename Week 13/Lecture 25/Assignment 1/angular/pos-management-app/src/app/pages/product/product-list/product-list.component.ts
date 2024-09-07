import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AgGridAngular } from '@ag-grid-community/angular';
import { ClientSideRowModelModule } from '@ag-grid-community/client-side-row-model';
import { ColDef, ModuleRegistry } from '@ag-grid-community/core';
import { ProductService } from '../../../services/product.service';
import { Router } from '@angular/router';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { ProductAddComponent } from '../product-add/product-add.component';
import { getCurrentTimestamp } from '../../../core/util/date-time.util';

import '@ag-grid-community/styles/ag-grid.css';
import '@ag-grid-community/styles/ag-theme-alpine.css';
import { MatSnackBarModule, MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [CommonModule, AgGridAngular, MatDialogModule, MatSnackBarModule],
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.scss',
  encapsulation: ViewEncapsulation.None
})
export class ProductListComponent implements OnInit {
  themeClass =  'ag-theme-alpine';

  selectedFile: File | null = null;

  colDefs: ColDef[] = [
    { field: 'id', sortable: true, filter: true },
    { field: 'name', sortable: true, filter: 'agTextColumnFilter' },
    { field: 'price', sortable: true, filter: 'agNumberColumnFilter' },
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
        onStatusChange: (product: any) => {
          product.status = product.status === 'ACTIVE' ? 'INACTIVE' : 'ACTIVE';
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

  constructor(private productService: ProductService, private router: Router, private dialog: MatDialog, private snackBar: MatSnackBar) {
    ModuleRegistry.registerModules([ClientSideRowModelModule]);
  }

  ngOnInit(): void {
      this.loadProducts();
  }

  loadProducts(): void {
    this.productService.getAll().subscribe(data => {
      this.rowData = data;
    });
  }

  onCellClicked(event: any): void {
    const action = event.event.target.getAttribute('data-action');
    if (action === 'edit') {
      this.editProduct(event.data);
    } else if (action === 'delete') {
      this.deleteProduct(event.data);
    } else if (event.colDef.field === 'status') {
      this.toggleProductStatus(event.data);
    }
  }

  editProduct(product: any): void {
    this.router.navigate([`/product/${product.id}`]);
  }

  toggleProductStatus(product: any): void {
    const updatedProduct = {
      ...product,
      status: product.status === 'ACTIVE' ? 'INACTIVE' : 'ACTIVE',
      updatedTime: getCurrentTimestamp()};
    this.productService.update(product.id, updatedProduct).subscribe(() => {
      this.snackBar.open('Status changed successfully', 'Close', {
        duration: 3000,
        horizontalPosition: 'center',
        verticalPosition: 'top'
      });
      this.loadProducts();
    });
  }

  deleteProduct(product: any): void {
    if (confirm(`Do you want to delete ${product.name}?`)) {
      this.snackBar.open('Product deleted!', 'Close', {
        duration: 3000,
        horizontalPosition: 'center',
        verticalPosition: 'top'
      });
    }
  }

  addProduct(): void {
    if (this.dialog.openDialogs.length === 0) {
      const dialogRef = this.dialog.open(ProductAddComponent, {
        width: '400px',
        panelClass: 'center-dialog-container',
      });

      dialogRef.afterClosed().subscribe((result) => {
        if (result === true) {
          this.loadProducts();
        }
      });
    }
  }

  onFileSelected(event: any): void {
    this.selectedFile = event.target.files[0];
  }

  onUpload(): void {
    if (this.selectedFile) {
      this.productService.uploadFile(this.selectedFile).subscribe(() => {
        this.snackBar.open('File uploaded successfully!', 'Close', {
          duration: 3000,
          horizontalPosition: 'center',
          verticalPosition: 'top'
        });
        this.loadProducts();
      });
    }
  }
}
