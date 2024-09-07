import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RevenueInputDialogComponent } from './revenue-input-dialog.component';

describe('RevenueInputDialogComponent', () => {
  let component: RevenueInputDialogComponent;
  let fixture: ComponentFixture<RevenueInputDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RevenueInputDialogComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RevenueInputDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
