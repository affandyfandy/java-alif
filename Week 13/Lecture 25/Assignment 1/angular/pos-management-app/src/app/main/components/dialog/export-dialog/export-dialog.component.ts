import { Component, inject, Input, model, OnInit } from '@angular/core';
import { MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { User } from '../../../../models/user.model';
import { MatFormFieldModule, MatLabel } from '@angular/material/form-field';
import { CustomerService } from '../../../../services/customer.service';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-export-dialog',
  standalone: true,
  templateUrl: './export-dialog.component.html',
  styleUrl: './export-dialog.component.scss',
  imports: [
    MatDialogModule,
    MatFormFieldModule,
    MatLabel,
    MatSelectModule,
    MatButtonModule,
    CommonModule,
    FormsModule
  ]
})
export class ExportDialogComponent implements OnInit {

  readonly dialogRef = inject(MatDialogRef<ExportDialogComponent>);

  selectedCustomer: string | null = null;
  selectedMonth: number | null = null;
  selectedYear: number | null = null;

  customers: any[] = [];

  constructor(private customerService: CustomerService) {}

  ngOnInit(): void {
    this.customerService.getAll().subscribe((customers) => {
      this.customers = customers;
    });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  export(): { customer: string | null, month: number | null, year: number | null } {
    return {
      customer: this.selectedCustomer,
      month: this.selectedMonth,
      year: this.selectedYear
    };
  }
}
