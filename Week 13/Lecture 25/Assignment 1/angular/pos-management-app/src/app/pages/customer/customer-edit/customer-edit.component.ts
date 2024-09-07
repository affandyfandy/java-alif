import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { CustomerService } from '../../../services/customer.service';
import { Router } from '@angular/router';
import { getCurrentTimestamp } from '../../../core/util/date-time.util';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';

@Component({
  selector: 'app-customer-edit',
  standalone: true,
  imports: [CommonModule, FormsModule, MatSnackBarModule],
  templateUrl: './customer-edit.component.html',
  styleUrl: './customer-edit.component.scss'
})
export class CustomerEditComponent {
  @Input() customer: any;
  @Output() customerUpdated = new EventEmitter<any>();

  phonePattern = "^\\+62\\d{9,13}$";

  constructor(private customerService: CustomerService, private router: Router, private snackBar: MatSnackBar) {}

  updateCustomer(form: NgForm): void {
    if (form.valid) {
      this.customer.updatedTime = getCurrentTimestamp();

      this.customerService.changeStatus(this.customer.id, this.customer).subscribe(() => {
        this.customerService.update(this.customer.id, this.customer).subscribe(updatedCustomer => {
          this.customerUpdated.emit(updatedCustomer);
          this.snackBar.open('Customer updated!', 'Close', {
            duration: 3000,
            horizontalPosition: 'center',
            verticalPosition: 'top'
          });
          this.router.navigate(['/customer']);
        });
      })
    } else {
      this.snackBar.open('Please fill out the form correctly!', 'Close', {
        duration: 3000,
        horizontalPosition: 'center',
        verticalPosition: 'top'
      });
    }
  }

  deleteCustomer(): void {
    if (confirm(`Do you want to delete ${this.customer.name}?`)) {
      this.customerService.delete(this.customer.id).subscribe({
        next: () => {
          this.snackBar.open('Customer deleted!', 'Close', {
            duration: 3000,
            horizontalPosition: 'center',
            verticalPosition: 'top'
          });
          setTimeout(() => {
            this.router.navigate(['/customer']);
          }, 0);
        },
        error: (err) => {
          console.error("Delete failed with error:", err);
          this.snackBar.open('Failed to delete customer!', 'Close', {
            duration: 3000,
            horizontalPosition: 'center',
            verticalPosition: 'top'
          });
          setTimeout(() => {
            this.router.navigate(['/customer']);
          }, 0);
        },
        complete: () => {
          console.log("Delete operation completed");
        }
      });
    }
  }
}
