import { Component } from '@angular/core';
import { LoginService } from '../../../services/auth.service';
import { Router } from '@angular/router';
import { RouterConfig } from '../../../config/app.constants';

@Component({
  selector: 'app-header',
  standalone: true,
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {
  username: string | null;

  constructor(private loginService: LoginService, private router: Router) {
    this.username = this.capitalizeWords(this.loginService.getUsername() ?? 'Guest');
  }

  logout(): void {
    this.loginService.logout();
    this.router.navigate([RouterConfig.LOGIN.link]);
  }

  private capitalizeWords(input: string): string {
    return input
        .split(' ')
        .map(word =>
            word.charAt(0).toUpperCase() + word.slice(1).toLowerCase()
        )
        .join(' ');
  }
}
