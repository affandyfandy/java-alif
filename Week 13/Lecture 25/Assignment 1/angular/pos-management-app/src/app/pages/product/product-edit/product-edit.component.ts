import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProductService } from '../../../services/product.service';
import { Router } from '@angular/router';
import { getCurrentTimestamp } from '../../../core/util/date-time.util';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';

@Component({
  selector: 'app-product-edit',
  standalone: true,
  imports: [CommonModule, FormsModule, MatSnackBarModule],
  templateUrl: './product-edit.component.html',
  styleUrl: './product-edit.component.scss'
})
export class ProductEditComponent {
  @Input() product: any;
  @Output() productUpdated = new EventEmitter<any>();

  constructor(private productService: ProductService, private router: Router, private snackBar: MatSnackBar) {}

  updateProduct(): void {
    this.product.updatedTime = getCurrentTimestamp();

    this.productService.update(this.product.id, this.product).subscribe(updatedProduct => {
      this.productUpdated.emit(updatedProduct);
      this.snackBar.open('Product updated!', 'Close', {
        duration: 3000,
        horizontalPosition: 'center',
        verticalPosition: 'top'
      });
      this.router.navigate(['/product']);
    });
  }

  deleteProduct(): void {
    if (confirm(`Do you want to delete ${this.product.name}?`)) {
      this.productService.delete(this.product.id).subscribe(() => {
        this.snackBar.open('Product deleted!', 'Close', {
          duration: 3000,
          horizontalPosition: 'center',
          verticalPosition: 'top'
        });
        this.router.navigate(['/product']);
      });
    }
  }
}
