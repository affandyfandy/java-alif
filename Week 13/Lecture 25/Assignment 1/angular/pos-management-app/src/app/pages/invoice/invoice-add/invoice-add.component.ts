import { Component } from '@angular/core';
import { AbstractControl, FormArray, FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatDialogRef } from '@angular/material/dialog';
import { getCurrentTimestamp } from '../../../core/util/date-time.util';
import { InvoiceService } from '../../../services/invoice.service';
import { Product } from '../../../models/product.model';
import { ProductService } from '../../../services/product.service';
import { CustomerService } from '../../../services/customer.service';
import { NgFor } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';

export function stringToDate(timestamp: string): Date {
  return new Date(timestamp.replace(' ', 'T'));
}

@Component({
  selector: 'app-invoice-add',
  standalone: true,
  imports: [ReactiveFormsModule, MatCardModule, NgFor],
  templateUrl: './invoice-add.component.html',
  styleUrl: './invoice-add.component.scss'
})
export class InvoiceAddComponent {
  invoiceForm: FormGroup;
  customers: any[] = [];
  products: any[] = [];

  constructor(
    private fb: FormBuilder,
    private invoiceService: InvoiceService,
    private productService: ProductService,
    private customerService: CustomerService,
    private dialogRef: MatDialogRef<InvoiceAddComponent>,
    private snackBar: MatSnackBar
  ) {
    this.invoiceForm = this.fb.group({
      customer_id: ['', Validators.required],
      invoice_amount: [{ value: '', disabled: true }, [Validators.required, Validators.min(0)]],
      selectedProducts: this.fb.array([], this.validateProducts)  // Apply the custom validator here
    });
  }

  ngOnInit(): void {
    this.loadCustomers();
    this.loadProducts();

    this.invoiceForm.get('selectedProducts')?.valueChanges.subscribe(() => {
      this.updateInvoiceAmount();
    });
  }

  validateProducts(formArray: AbstractControl): { [key: string]: boolean } | null {
    const products = (formArray as FormArray).controls;
    if (products.length === 0) {
      return { noProducts: true };
    }

    const invalidQuantity = products.some(control => control.get('quantity')?.value <= 0);
    if (invalidQuantity) {
      return { invalidQuantity: true };
    }

    return null;
  }

  loadCustomers() {
    this.customerService.getAll().subscribe(customers => {
      this.customers = customers;
      if (this.customers.length > 0) {
        this.invoiceForm.get('customer_id')?.setValue(this.customers[0].id);
      }
    });
  }

  loadProducts() {
    this.productService.getAll().subscribe(products => {
      this.products = products;
    });
  }

  onProductSelect(event: Event) {
    const selectedProductId = (event.target as HTMLSelectElement).value;
    const product = this.products.find(p => p.id === selectedProductId);

    if (product) {
      this.addProduct(product);
    }
  }

  getProductById(productId: string): Product | undefined {
    return this.products.find(p => p.id === productId);
  }

  get selectedProducts(): FormArray {
    return this.invoiceForm.get('selectedProducts') as FormArray;
  }

  addProduct(product: any) {
    const productFormGroup = this.fb.group({
      product_id: [product.id, Validators.required],
      quantity: [1, [Validators.required, Validators.min(1)]],
      amount: [{ value: product.price, disabled: true }]
    });
    this.selectedProducts.push(productFormGroup);
    this.updateInvoiceAmount();
  }

  removeProduct(index: number) {
    this.selectedProducts.removeAt(index);
    this.updateInvoiceAmount();
  }

  updateInvoiceAmount() {
    let totalAmount = 0;
    this.selectedProducts.controls.forEach((control) => {
      const quantity = control.get('quantity')?.value || 0;
      const amount = control.get('amount')?.value || 0;
      totalAmount += quantity * amount;
    });
    this.invoiceForm.get('invoice_amount')?.setValue(totalAmount);
  }

  onSubmit(): void {
    if (this.invoiceForm.valid) {
      const customer = this.customers.find(c => c.id === this.invoiceForm.value.customer_id);

      const selectedProducts = this.selectedProducts.controls.map((control) => {
        const product = this.products.find(p => p.id === control.get('product_id')?.value);
        return {
          productId: product.id,
          productName: product.name,
          price: product.price,
          quantity: control.get('quantity')?.value,
          amount: control.get('quantity')?.value * product.price
        };
      });

      const newInvoice = {
        customerId: customer.id,
        invoiceDate: getCurrentTimestamp().slice(0, 10),
        invoiceAmount: this.invoiceForm.get('invoice_amount')?.value,
        products: selectedProducts,
      };

      this.invoiceService.create(newInvoice).subscribe({
        next: () => {
          this.snackBar.open('Invoice added!', 'Close', {
            duration: 3000,
            horizontalPosition: 'center',
            verticalPosition: 'top'
          });
          this.dialogRef.close(true);
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
    }
  }

  onCancel(): void {
    this.dialogRef.close(false);
  }

  asFormGroup(control: AbstractControl): FormGroup {
    return control as FormGroup;
  }
}


