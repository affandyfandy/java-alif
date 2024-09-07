import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { MatFormField } from '@angular/material/form-field';
import { FormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';

@Component({
  selector: 'app-revenue-input-dialog',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatFormField,
    MatInputModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatButtonModule,
    MatCardModule
  ],
  templateUrl: './revenue-input-dialog.component.html',
  styleUrl: './revenue-input-dialog.component.scss'
})
export class RevenueInputDialogComponent {
  selectedDate: string = '';
  selectedYear: number = new Date().getFullYear();
  selectedMonth: number = new Date().getMonth() + 1;

  constructor(private dialogRef: MatDialogRef<RevenueInputDialogComponent>) {}

  closeDialog(): void {
    this.dialogRef.close();
  }

  submit(): void {
    this.dialogRef.close({
      date: this.selectedDate,
      year: this.selectedYear,
      month: this.selectedMonth,
    });
  }
}
