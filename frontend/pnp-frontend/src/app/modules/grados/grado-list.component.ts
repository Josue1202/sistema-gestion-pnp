import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { GradoService, GradoPolicial } from '../../core/services/grado.service';

@Component({
    selector: 'app-grado-list',
    standalone: true,
    imports: [CommonModule, FormsModule],
    templateUrl: './grado-list.component.html',
    styleUrls: ['./grado-list.component.css']
})
export class GradoListComponent implements OnInit {
    grados: GradoPolicial[] = [];
    filteredGrados: GradoPolicial[] = [];
    searchQuery = '';
    loading = false;

    // Modal
    showModal = false;
    isEditing = false;
    formGrado: GradoPolicial = this.getEmptyForm();

    categorias = ['OFICIAL', 'SUBOFICIAL', 'CIVIL'];

    constructor(
        private gradoService: GradoService,
        private toastr: ToastrService
    ) { }

    ngOnInit(): void {
        this.loadGrados();
    }

    loadGrados(): void {
        this.loading = true;
        this.gradoService.getAll().subscribe({
            next: (data) => {
                this.grados = data;
                this.filteredGrados = data;
                this.loading = false;
            },
            error: (err) => {
                console.error('Error cargando grados:', err);
                this.toastr.error('Error al cargar grados', 'Error');
                this.loading = false;
            }
        });
    }

    search(): void {
        if (!this.searchQuery.trim()) {
            this.filteredGrados = this.grados;
            return;
        }

        const query = this.searchQuery.toLowerCase();
        this.filteredGrados = this.grados.filter(g =>
            g.nombre.toLowerCase().includes(query) ||
            g.categoria.toLowerCase().includes(query) ||
            g.jerarquia.toString().includes(query)
        );
    }

    clearSearch(): void {
        this.searchQuery = '';
        this.search();
    }

    openModalCreate(): void {
        this.isEditing = false;
        this.formGrado = this.getEmptyForm();
        this.showModal = true;
    }

    openModalEdit(grado: GradoPolicial): void {
        this.isEditing = true;
        this.formGrado = { ...grado };
        this.showModal = true;
    }

    closeModal(): void {
        this.showModal = false;
    }

    save(): void {
        if (!this.formGrado.nombre || !this.formGrado.categoria || !this.formGrado.jerarquia) {
            this.toastr.warning('Todos los campos son obligatorios', 'Atención');
            return;
        }

        if (this.isEditing && this.formGrado.idGrado) {
            this.gradoService.update(this.formGrado.idGrado, this.formGrado).subscribe({
                next: (updated) => {
                    const index = this.grados.findIndex(g => g.idGrado === updated.idGrado);
                    if (index !== -1) {
                        this.grados[index] = updated;
                    }
                    this.search();
                    this.closeModal();
                    this.toastr.success('Grado actualizado correctamente', 'Éxito');
                },
                error: (err) => {
                    console.error('Error actualizando:', err);
                    this.toastr.error('Error al actualizar el grado', 'Error');
                }
            });
        } else {
            this.gradoService.create(this.formGrado).subscribe({
                next: (created) => {
                    this.grados.push(created);
                    this.search();
                    this.closeModal();
                    this.toastr.success('Grado creado correctamente', 'Éxito');
                },
                error: (err) => {
                    console.error('Error creando:', err);
                    this.toastr.error('Error al crear el grado', 'Error');
                }
            });
        }
    }

    delete(id?: number): void {
        if (!id) return;
        if (!confirm('¿Eliminar este grado? Esta acción puede afectar al personal asignado.')) return;

        this.gradoService.delete(id).subscribe({
            next: () => {
                this.grados = this.grados.filter(g => g.idGrado !== id);
                this.search();
                this.toastr.success('Grado eliminado correctamente', 'Éxito');
            },
            error: (err) => {
                console.error('Error eliminando:', err);
                this.toastr.error('Error al eliminar el grado', 'Error');
            }
        });
    }

    getEmptyForm(): GradoPolicial {
        return {
            nombre: '',
            jerarquia: 1,
            categoria: 'OFICIAL'
        };
    }

    getCategoriaLabel(categoria: string): string {
        const labels: any = {
            'OFICIAL': 'Oficial',
            'SUBOFICIAL': 'Suboficial',
            'CIVIL': 'Civil'
        };
        return labels[categoria] || categoria;
    }

    getCategoriaClass(categoria: string): string {
        const classes: any = {
            'OFICIAL': 'badge-oficial',
            'SUBOFICIAL': 'badge-suboficial',
            'CIVIL': 'badge-civil'
        };
        return classes[categoria] || '';
    }
}
