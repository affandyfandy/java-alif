import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { CustomerService } from '../../../services/customer.service';
import { MatCardModule } from '@angular/material/card';
import { getCurrentTimestamp } from '../../../core/util/date-time.util';
import { NgIf } from '@angular/common';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';

@Component({
  selector: 'app-customer-add',
  standalone: true,
  imports: [ReactiveFormsModule, MatCardModule, NgIf, MatSnackBarModule],
  templateUrl: './customer-add.component.html',
  styleUrl: './customer-add.component.scss'
})
export class CustomerAddComponent {
  customerForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private customerService: CustomerService,
    private dialogRef: MatDialogRef<CustomerAddComponent>,
    private snackBar: MatSnackBar
  ) {
    this.customerForm = this.fb.group({
      name: ['', Validators.required],
      phoneNumber: ['',[
        Validators.required,
        Validators.pattern(/^\+62\d{9,13}$/)
      ]],
      status: ['ACTIVE', Validators.required],
    });
  }

  onSubmit(): void {
    if (this.customerForm.valid) {
      const newCustomer = {
        ...this.customerForm.value,
        createdTime: getCurrentTimestamp(),
        updatedTime: getCurrentTimestamp(),
      };

      this.customerService.create(newCustomer).subscribe(() => {
        this.snackBar.open('Customer added!', 'Close', {
          duration: 3000,
          horizontalPosition: 'center',
          verticalPosition: 'top'
        });
        this.dialogRef.close(true);
      });
    }
  }

  onCancel(): void {
    this.dialogRef.close(false);
  }
}
