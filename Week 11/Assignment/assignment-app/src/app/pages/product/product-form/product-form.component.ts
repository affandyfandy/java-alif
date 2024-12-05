import { Component, Input, OnInit, SimpleChanges } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ProductService } from '../../../services/product.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from '../../../models/product';

@Component({
  selector: 'app-product-form',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './product-form.component.html',
  styleUrl: './product-form.component.scss'
})
export class ProductFormComponent implements OnInit {
  @Input() product: any;
  
  productForm: FormGroup;
  isEdit: boolean = false;
  productId: string | null = null;

  constructor(
    private fb: FormBuilder, 
    private productService: ProductService, 
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.productForm = this.fb.group({
      name: ['', Validators.required],
      price: ['', [Validators.required, Validators.min(0)]],
      status: [true, Validators.required]
    });
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.productId = params.get('id');
      if (this.productId) {
        this.isEdit = true;
        this.loadProductData(this.productId);
      }
    })
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['product'] && this.product) {
      this.productForm.patchValue(this.product);
    }
  }

  loadProductData(id: string) {
    this.productService.getProductById(id).subscribe((product: Product) => {
      this.productForm.patchValue(product);
    });
  }

  onSubmit() {
    if (this.productForm.valid) {
      const productData = this.productForm.value as Product;
      if (this.isEdit && this.productId) {
        this.productService.updateProduct({ ...productData, id: this.productId }).subscribe(() => {
          this.router.navigate(['/products']);
        });
      } else {
        this.productService.generateId().subscribe(newId => {
          productData.id = newId;
          this.productService.addProduct(productData).subscribe(() => {
            this.router.navigate(['/products']);
          });
        });
      }
    }
  }

  cancel() {
    this.router.navigate(['/products']);
  }
}
