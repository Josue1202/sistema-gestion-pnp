import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

@Component({
    selector: 'app-login',
    standalone: true,
    imports: [CommonModule, FormsModule],
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css']
})
export class LoginComponent {
    credentials = {
        username: '',
        password: ''
    };

    errorMessage = '';
    loading = false;

    constructor(
        private authService: AuthService,
        private router: Router
    ) { }

    onSubmit(): void {
        if (!this.credentials.username || !this.credentials.password) {
            this.errorMessage = 'Por favor complete todos los campos';
            return;
        }

        this.loading = true;
        this.errorMessage = '';

        this.authService.login(this.credentials).subscribe({
            next: (response) => {
                this.loading = false;
                this.router.navigate(['/dashboard']);
            },
            error: (error) => {
                this.loading = false;
                this.errorMessage = error.error?.error || 'Error al iniciar sesión';
            }
        });
    }
}
