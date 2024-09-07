import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, OnChanges, Output, SimpleChanges } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { InvoiceService } from '../../../services/invoice.service';
import { ActivatedRoute, Router } from '@angular/router'
import { getCurrentTimestamp } from '../../../core/util/date-time.util';
import { HttpErrorResponse } from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-invoice-edit',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './invoice-edit.component.html',
  styleUrl: './invoice-edit.component.scss'
})
export class InvoiceEditComponent implements OnChanges {
  @Input() invoice: any;
  @Input() productId: number | null = null;
  @Output() invoiceUpdated = new EventEmitter<any>();

  selectedProductIndex: number | null = null;

  constructor(
    private invoiceService: InvoiceService,
    private router: Router,
    private route: ActivatedRoute,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    const invoiceId = this.route.snapshot.paramMap.get('id');
    if (invoiceId) {
      this.invoiceService.get(invoiceId).subscribe(invoice => {
        this.invoice = invoice;
      });
    }
  }

  onProductChange(index: number): void {
    this.selectedProductIndex = index;
  }

  onQuantityChange(): void {
    if (this.selectedProductIndex !== null) {
      const product = this.invoice.products[this.selectedProductIndex];
      product.amount = product.quantity * product.price;
      this.updateInvoiceAmount();
      this.invoiceUpdated.emit(this.invoice);
    }
  }

  updateInvoiceAmount(): void {
    let totalAmount = 0;
    this.invoice.products.forEach((product: any) => {
      totalAmount += product.amount;
    });
    this.invoice.invoiceAmount = totalAmount;
  }

  updateInvoice(): void {
    this.invoice.updatedTime = getCurrentTimestamp();
    this.invoice.products.forEach((product: any) => {
      this.invoiceService.update(this.invoice.invoiceId, product.productId, product)
        .subscribe({
          next: (updatedProduct) => {
            this.invoiceUpdated.emit(updatedProduct);
            this.snackBar.open('Invoice updated!', 'Close', {
              duration: 3000,
              horizontalPosition: 'center',
              verticalPosition: 'top'
            });
            this.router.navigate(['/invoice']);
          },
          error: (errorResponse: HttpErrorResponse) => {
            if (errorResponse.error && errorResponse.error.errors) {
              const errorMessage = errorResponse.error.errors;
              this.snackBar.open(errorMessage, 'Close', {
                duration: 4000,
                horizontalPosition: 'center',
                verticalPosition: 'top',
                panelClass: 'snackbar'
              });
            } else {
              console.log('Unexpected error structure:', errorResponse);
            }
          }
        });
    });
  }

  deleteInvoice(): void {
    if (confirm('Do you want to delete this invoice?')) {
      this.invoiceService.deleteInvoice(this.invoice.invoiceId).subscribe(() => {
        this.snackBar.open('Invoice deleted!', 'Close', {
          duration: 3000,
          horizontalPosition: 'center',
          verticalPosition: 'top'
        });
        this.router.navigate(['/invoice']);
      });
    }
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['productId'] && changes['productId'].currentValue) {
      this.selectedProductIndex = this.invoice.products.findIndex((product: any) => product.productId === this.productId);
    }
  }
}
