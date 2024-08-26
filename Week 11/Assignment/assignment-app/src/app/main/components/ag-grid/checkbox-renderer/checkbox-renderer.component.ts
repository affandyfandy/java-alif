import { Component } from '@angular/core';
import { ICellRendererParams } from 'ag-grid-community';

@Component({
  selector: 'app-checkbox-renderer',
  template: `<input type="checkbox" [checked]="params.value" (change)="onChange($event)">`
})
export class CheckboxRendererComponent {
  params!: ICellRendererParams;

  agInit(params: ICellRendererParams): void {
    this.params = params;
  }

  onChange(event: Event): void {
    const checkbox = event.target as HTMLInputElement;
    if (this.params && this.params.data) {
      this.params.data.status = checkbox.checked;

      // Safely access column and data
      const colId = this.params.column?.getColId();
      if (colId) {
        this.params.node.setDataValue(colId, checkbox.checked);
        this.params.api.applyTransaction({ update: [this.params.data] });
        this.params.context.componentParent.onCellValueChanged(this.params);
      } else {
        console.error('Column ID is not available.');
      }
    } else {
      console.error('Params or data is not available.');
    }
  }
}
