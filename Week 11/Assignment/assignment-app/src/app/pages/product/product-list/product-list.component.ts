import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { AgGridAngular, ICellRendererAngularComp } from 'ag-grid-angular';
import { CellValueChangedEvent, ColDef, ICellRendererParams } from 'ag-grid-community';
import { ProductService } from '../../../services/product.service';
import 'ag-grid-community/styles/ag-grid.css';
import 'ag-grid-community/styles/ag-theme-alpine.css';
import { Router } from '@angular/router';

@Component({
  standalone: true,
  template: `<button type="button" class="btn btn-warning" (click)="buttonClicked()">Edit</button>`
})
export class EditButtonComponent implements ICellRendererAngularComp {
  private params: ICellRendererParams<any, any, any> | undefined;

  constructor(private router: Router) {}

  agInit(params: ICellRendererParams<any, any, any>): void {
    this.params = params;
  }

  refresh(params: ICellRendererParams<any, any, any>): boolean {
    return true;
  }

  buttonClicked(): void {
    if (this.params) {
      this.router.navigate(['/products', 'edit', this.params.data.id]);
    }
  }
}

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [
    AgGridAngular
  ],
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.scss'
})
export class ProductListComponent implements OnInit {
  @Output() productSelected = new EventEmitter<any>();

  products: any[] = [];
  columnDefs: ColDef[] = [
    { headerName: 'ID', field: 'id', sortable: true, filter: true },
    { headerName: 'Name', field: 'name', sortable: true, filter: 'agTextColumnFilter', },
    { headerName: 'Price', field: 'price', sortable: true, filter: 'agTextColumnFilter',  },
    { headerName: 'Status', 
      field: 'status',
      cellEditor: 'agSelectCellEditor',
      editable: true,
      cellEditorParams: {
        values: ['Active', 'Inactive']
      },
      sortable: true,
      filter: 'agTextColumnFilter'
    }
  ];
  
  paginationPageSizeSelector: number[] | boolean = [3, 5, 10];
  defaultColDef: ColDef = {
    flex: 1,
    filter: true,
    sortable: true,
    floatingFilter: true
  };

  constructor(private productService: ProductService){}

  ngOnInit(): void {
    this.productService.getProducts().subscribe(products => this.products = products);
  }

  onCellValueChanged(event: CellValueChangedEvent): void {
    this.productService.updateProduct(event.data).subscribe();
  }

  onRowClicked(event: any): void {
    this.productSelected.emit(event.data);
  }
}
