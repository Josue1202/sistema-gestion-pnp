import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { PersonalService } from '../../../core/services/personal.service';
import { PersonalPNP } from '../../../core/models/models';

@Component({
    selector: 'app-personal-list',
    standalone: true,
    imports: [CommonModule, FormsModule],
    templateUrl: './personal-list.component.html',
    styleUrls: ['./personal-list.component.css']
})
export class PersonalListComponent implements OnInit {
    personalList: PersonalPNP[] = [];
    filteredList: PersonalPNP[] = [];
    searchQuery = '';
    loading = false;
    errorMessage = '';
    stats: any = null;

    constructor(
        private personalService: PersonalService,
        private router: Router
    ) { }

    ngOnInit(): void {
        this.loadPersonal();
        this.loadStats();
    }

    loadPersonal(): void {
        this.loading = true;
        this.errorMessage = '';

        this.personalService.getAllPersonal().subscribe({
            next: (data) => {
                this.personalList = data;
                this.filteredList = data;
                this.loading = false;
            },
            error: (error) => {
                this.errorMessage = 'Error al cargar el personal';
                this.loading = false;
            }
        });
    }

    loadStats(): void {
        this.personalService.getStats().subscribe({
            next: (data) => {
                this.stats = data;
            },
            error: (error) => {
                console.error('Error al cargar estadísticas', error);
            }
        });
    }

    onSearch(): void {
        if (!this.searchQuery.trim()) {
            this.filteredList = this.personalList;
            return;
        }

        this.personalService.searchPersonal(this.searchQuery).subscribe({
            next: (data) => {
                this.filteredList = data;
            },
            error: (error) => {
                console.error('Error en búsqueda', error);
            }
        });
    }

    clearSearch(): void {
        this.searchQuery = '';
        this.filteredList = this.personalList;
    }

    viewDetails(id: number): void {
        this.router.navigate(['/personal', id]);
    }

    editPersonal(id: number): void {
        this.router.navigate(['/personal', 'edit', id]);
    }

    deletePersonal(id: number, nombre: string): void {
        if (confirm(`¿Está seguro de eliminar a ${nombre}?`)) {
            this.personalService.deletePersonal(id).subscribe({
                next: () => {
                    this.loadPersonal();
                    alert('Personal eliminado exitosamente');
                },
                error: (error) => {
                    alert('Error al eliminar personal');
                }
            });
        }
    }

    createNew(): void {
        this.router.navigate(['/personal', 'new']);
    }

    getEstadoBadgeClass(estado: string): string {
        switch (estado) {
            case 'ACTIVO':
                return 'badge-success';
            case 'INACTIVO':
                return 'badge-error';
            case 'LICENCIA':
                return 'badge-warning';
            default:
                return 'badge-info';
        }
    }
}
