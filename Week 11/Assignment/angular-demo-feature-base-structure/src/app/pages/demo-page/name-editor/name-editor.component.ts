import {Component} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule} from '@angular/forms';
import { forbiddenNameValidator } from '../../../core/directives/validation/nameValidator';
import { ForbiddenValidatorDirective } from '../../../core/directives/validation/forbidden-validator.directive';

@Component({
  selector: 'app-name-editor',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    ForbiddenValidatorDirective
  ],
  templateUrl: './name-editor.component.html',
  styleUrls: ['./name-editor.component.scss']
})
export class NameEditorComponent {
  name = new FormControl('');

  profileForm = new FormGroup({
    firstName: new FormControl('', [forbiddenNameValidator(/invalid/i)]),
    email: new FormControl(''),
  });
  constructor() { }

  onSubmit() {
    // TODO: Use EventEmitter with form value
    console.warn(this.profileForm.value);
  }

  updateProfile() {
    this.profileForm.patchValue({
      firstName: 'Nancy',
      email: 'Lee@gmail.com'
    });
  }
}
