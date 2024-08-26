import { Component } from '@angular/core';
import { HighlightDirective } from '../../../core/directives/highlight.directive';
import { DatePipe } from '@angular/common';
import { PhonePipe } from '../../../core/pipes/phone.pipe';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-demo',
  standalone: true,
  imports: [
    HighlightDirective,
    DatePipe,
    PhonePipe,
    FormsModule
  ],
  templateUrl: './demo.component.html',
  styleUrl: './demo.component.scss'
})
export class DemoComponent {
  birthday = new Date();
  phoneNumber = '0778815468';
  power = 5;

}
