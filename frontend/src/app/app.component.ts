import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthComponent } from './auth.component';
import { ProductComponent } from './product.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, AuthComponent, ProductComponent],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class AppComponent {
  isLoggedIn(): boolean {
    return !!localStorage.getItem('token');
  }
}
