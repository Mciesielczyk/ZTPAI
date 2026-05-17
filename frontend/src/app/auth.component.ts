import { Component, EventEmitter, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from './auth.service';

@Component({
  selector: 'app-auth',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './auth.component.html',
})
export class AuthComponent {
  @Output() authenticated = new EventEmitter<void>();

  isLoginMode = true;
  username = '';
  email = '';
  password = '';
  role = 'USER';
  message = '';
  isError = false;

  constructor(private authService: AuthService) {}

  toggleMode() {
    this.isLoginMode = !this.isLoginMode;
    this.message = '';
    this.isError = false;
  }

  private extractErrorMessage(err: any, fallback: string): string {
    const response = err?.error;

    if (!response) {
      return fallback;
    }

    if (typeof response === 'string') {
      return response;
    }

    if (response.message) {
      return response.message;
    }

    if (Array.isArray(response.errors) && response.errors.length > 0) {
      return response.errors
        .map((item: any) => item?.defaultMessage || item?.message || JSON.stringify(item))
        .join(', ');
    }

    return fallback;
  }

  onSubmit() {
    this.message = '';

    if (this.isLoginMode) {
      const loginPayload = { username: this.username, password: this.password };
      console.log('Wysyłam do LOGIN:', loginPayload);

      this.authService.login(loginPayload).subscribe({
        next: (response) => {
          this.isError = false;
          localStorage.setItem('token', response.token);
          this.authenticated.emit();
        },
        error: (err) => {
          this.isError = true;
          this.message = this.extractErrorMessage(err, 'Błąd logowania!');
        }
      });
    } else {
      const registerPayload = {
        username: this.username.trim(),
        email: this.email.trim(),
        password: this.password,
        role: this.role
      };

      console.log('Wysyłam do REGISTER:', registerPayload);

      this.authService.register(registerPayload).subscribe({
        next: () => {
          this.isError = false;
          this.message = 'Rejestracja pomyślna! Możesz się zalogować.';
          this.isLoginMode = true;
          this.email = '';
          this.password = '';
          this.role = 'USER';
        },
        error: (err) => {
          this.isError = true;
          this.message = this.extractErrorMessage(err, 'Błąd rejestracji!');
        }
      });
    }
  }
}
