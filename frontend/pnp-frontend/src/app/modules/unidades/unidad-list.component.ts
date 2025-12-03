import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { UnidadService, UnidadPolicial } from '../../core/services/unidad.service';

@Component({
    selector: 'app-unidad-list',
    standalone: true,
    imports: [CommonModule, FormsModule],
    templateUrl: './unidad-list.component.html',
    styleUrls: ['./unidad-list.component.css']
})
export class UnidadListComponent implements OnInit {
    unidades: UnidadPolicial[] = [];
    filteredUnidades: UnidadPolicial[] = [];
    searchQuery = '';
    loading = false;

    // Modal
    showModal = false;
    isEditing = false;
    formUnidad: UnidadPolicial = this.getEmptyForm();

    tiposUnidad = ['COMISARIA', 'DIVISION', 'REGION', 'ADMINISTRATIVO', 'ESPECIAL'];

    constructor(
        private unidadService: UnidadService,
        private toastr: ToastrService
    ) { }

    ngOnInit(): void {
        this.loadUnidades();
    }

    loadUnidades(): void {
        this.loading = true;
        this.unidadService.getAll().subscribe({
            next: (data) => {
                this.unidades = data;
                this.filteredUnidades = data;
                this.loading = false;
            },
            error: (err) => {
                console.error('Error cargando unidades:', err);
                this.loading = false;
            }
        });
    }

    search(): void {
        if (!this.searchQuery.trim()) {
            this.filteredUnidades = this.unidades;
            return;
        }

        const query = this.searchQuery.toLowerCase();
        this.filteredUnidades = this.unidades.filter(u =>
            u.nombre.toLowerCase().includes(query) ||
            (u.siglas?.toLowerCase().includes(query)) ||
            u.tipo.toLowerCase().includes(query)
        );
    }

    clearSearch(): void {
        this.searchQuery = '';
        this.search();
    }

    openModalCreate(): void {
        this.isEditing = false;
        this.formUnidad = this.getEmptyForm();
        this.showModal = true;
    }

    openModalEdit(unidad: UnidadPolicial): void {
        this.isEditing = true;
        this.formUnidad = { ...unidad };
        this.showModal = true;
    }

    closeModal(): void {
        this.showModal = false;
    }

    save(): void {
        if (!this.formUnidad.nombre || !this.formUnidad.tipo) {
            alert('Nombre y tipo son obligatorios');
            return;
        }

        if (this.isEditing && this.formUnidad.idUnidad) {
            this.unidadService.update(this.formUnidad.idUnidad, this.formUnidad).subscribe({
                next: (updated) => {
                    const index = this.unidades.findIndex(u => u.idUnidad === updated.idUnidad);
                    if (index !== -1) {
                        this.unidades[index] = updated;
                    }
                    this.search();
                    this.closeModal();
                    this.toastr.success('Unidad actualizada correctamente', 'Éxito');
                },
                error: (err) => {
                    console.error('Error actualizando:', err);
                    this.toastr.error('Error al actualizar la unidad', 'Error');
                }
            });
        } else {
            this.unidadService.create(this.formUnidad).subscribe({
                next: (created) => {
                    this.unidades.push(created);
                    this.search();
                    this.closeModal();
                    this.toastr.success('Unidad creada correctamente', 'Éxito');
                },
                error: (err) => {
                    console.error('Error creando:', err);
                    this.toastr.error('Error al crear la unidad', 'Error');
                }
            });
        }
    }

    delete(id?: number): void {
        if (!id) return;
        if (!confirm('¿Eliminar esta unidad?')) return;

        this.unidadService.delete(id).subscribe({
            next: () => {
                this.unidades = this.unidades.filter(u => u.idUnidad !== id);
                this.search();
                this.toastr.success('Unidad eliminada correctamente', 'Éxito');
            },
            error: (err) => {
                console.error('Error eliminando:', err);
                this.toastr.error('Error al eliminar la unidad', 'Error');
            }
        });
    }

    getEmptyForm(): UnidadPolicial {
        return {
            nombre: '',
            siglas: '',
            tipo: 'COMISARIA'
        };
    }

    getTipoLabel(tipo: string): string {
        const labels: any = {
            'COMISARIA': 'Comisaría',
            'DIVISION': 'División',
            'REGION': 'Región',
            'ADMINISTRATIVO': 'Administrativo',
            'ESPECIAL': 'Especial'
        };
        return labels[tipo] || tipo;
    }
}
