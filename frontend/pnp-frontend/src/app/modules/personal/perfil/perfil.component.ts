import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { PersonalService } from '../../../core/services/personal.service';
import {
    AscensoService, AscensoDTO,
    ServicioService, ServicioDTO,
    CursoService, CursoDTO,
    FamiliarService, FamiliarDTO
} from '../../../core/services/entidades.service';

@Component({
    selector: 'app-perfil',
    standalone: true,
    imports: [CommonModule, FormsModule],
    templateUrl: './perfil.component.html',
    styleUrls: ['./perfil.component.css']
})
export class PerfilComponent implements OnInit {

    cip: string = '';
    personal: any = null;
    activeTab: string = 'datos';

    // Arrays de datos
    ascensos: AscensoDTO[] = [];
    servicios: ServicioDTO[] = [];
    cursos: CursoDTO[] = [];
    familiares: FamiliarDTO[] = [];

    // Estados de modales
    showModalAscenso = false;
    showModalServicio = false;
    showModalCurso = false;
    showModalFamiliar = false;

    // Formularios
    formAscenso: AscensoDTO = { grado: '', rd: '', fecha: '', motivo: '' };
    formServicio: ServicioDTO = { unidad: '', cargo: '', desde: '', hasta: '' };
    formCurso: CursoDTO = { tipo: 'INSTITUCIONAL', nombre: '', lugar: '', fecha: '' };
    formFamiliar: FamiliarDTO = { nombresApellidos: '', fechaNac: '', lugarNac: '', parentesco: 'HIJO' };

    constructor(
        private route: ActivatedRoute,
        private personalService: PersonalService,
        private ascensoService: AscensoService,
        private servicioService: ServicioService,
        private cursoService: CursoService,
        private familiarService: FamiliarService
    ) { }

    ngOnInit(): void {
        // Obtener ID desde la URL
        const id = this.route.snapshot.paramMap.get('id');
        if (id) {
            this.loadPersonal(+id);
        }
    }

    loadPersonal(id: number): void {
        // Cargar datos reales desde el backend
        this.personalService.getPersonalById(id).subscribe({
            next: (data) => {
                console.log('DEBUG PERFIL - Datos recibidos:', data);
                this.personal = data;
                this.cip = data.cip;
                this.loadEntidades();
            },
            error: (err) => console.error('Error cargando personal:', err)
        });
    }

    loadEntidades(): void {
        if (!this.personal?.idPersonal) return;

        const id = this.personal.idPersonal;

        this.ascensoService.getByPersonal(id).subscribe({
            next: (data) => this.ascensos = data,
            error: (err) => console.error('Error cargando ascensos:', err)
        });

        this.servicioService.getByPersonal(id).subscribe({
            next: (data) => this.servicios = data,
            error: (err) => console.error('Error cargando servicios:', err)
        });

        this.cursoService.getByPersonal(id).subscribe({
            next: (data) => this.cursos = data,
            error: (err) => console.error('Error cargando cursos:', err)
        });

        this.familiarService.getByPersonal(id).subscribe({
            next: (data) => this.familiares = data,
            error: (err) => console.error('Error cargando familiares:', err)
        });
    }

    // === ASCENSOS ===
    openModalAscenso(): void {
        this.formAscenso = { grado: '', rd: '', fecha: '', motivo: '' };
        this.showModalAscenso = true;
    }

    closeModalAscenso(): void {
        this.showModalAscenso = false;
    }

    agregarAscenso(): void {
        if (!this.personal?.idPersonal || !this.formAscenso.grado) return;

        this.ascensoService.create(this.personal.idPersonal, this.formAscenso).subscribe({
            next: (nuevo) => {
                this.ascensos.push(nuevo);
                this.closeModalAscenso();
            },
            error: (err) => console.error('Error agregando ascenso:', err)
        });
    }

    eliminarAscenso(id: number): void {
        if (!confirm('¿Eliminar este ascenso?')) return;

        this.ascensoService.delete(id).subscribe({
            next: () => {
                this.ascensos = this.ascensos.filter(a => a.idAscenso !== id);
            },
            error: (err) => console.error('Error eliminando ascenso:', err)
        });
    }

    // === SERVICIOS ===
    openModalServicio(): void {
        this.formServicio = { unidad: '', cargo: '', desde: '', hasta: '' };
        this.showModalServicio = true;
    }

    closeModalServicio(): void {
        this.showModalServicio = false;
    }

    agregarServicio(): void {
        if (!this.personal?.idPersonal || !this.formServicio.unidad) return;

        this.servicioService.create(this.personal.idPersonal, this.formServicio).subscribe({
            next: (nuevo) => {
                this.servicios.push(nuevo);
                this.closeModalServicio();
            },
            error: (err) => console.error('Error agregando servicio:', err)
        });
    }

    eliminarServicio(id: number): void {
        if (!confirm('¿Eliminar este servicio?')) return;

        this.servicioService.delete(id).subscribe({
            next: () => {
                this.servicios = this.servicios.filter(s => s.idServicio !== id);
            },
            error: (err) => console.error('Error eliminando servicio:', err)
        });
    }

    // === CURSOS ===
    openModalCurso(): void {
        this.formCurso = { tipo: 'INSTITUCIONAL', nombre: '', lugar: '', fecha: '' };
        this.showModalCurso = true;
    }

    closeModalCurso(): void {
        this.showModalCurso = false;
    }

    agregarCurso(): void {
        if (!this.personal?.idPersonal || !this.formCurso.nombre) return;

        this.cursoService.create(this.personal.idPersonal, this.formCurso).subscribe({
            next: (nuevo) => {
                this.cursos.push(nuevo);
                this.closeModalCurso();
            },
            error: (err) => console.error('Error agregando curso:', err)
        });
    }

    eliminarCurso(id: number): void {
        if (!confirm('¿Eliminar este curso?')) return;

        this.cursoService.delete(id).subscribe({
            next: () => {
                this.cursos = this.cursos.filter(c => c.idCurso !== id);
            },
            error: (err) => console.error('Error eliminando curso:', err)
        });
    }

    // === FAMILIARES ===
    openModalFamiliar(): void {
        this.formFamiliar = { nombresApellidos: '', fechaNac: '', lugarNac: '', parentesco: 'HIJO' };
        this.showModalFamiliar = true;
    }

    closeModalFamiliar(): void {
        this.showModalFamiliar = false;
    }

    agregarFamiliar(): void {
        if (!this.personal?.idPersonal || !this.formFamiliar.nombresApellidos) return;

        this.familiarService.create(this.personal.idPersonal, this.formFamiliar).subscribe({
            next: (nuevo) => {
                this.familiares.push(nuevo);
                this.closeModalFamiliar();
            },
            error: (err) => console.error('Error agregando familiar:', err)
        });
    }

    eliminarFamiliar(id: number): void {
        if (!confirm('¿Eliminar este familiar?')) return;

        this.familiarService.delete(id).subscribe({
            next: () => {
                this.familiares = this.familiares.filter(f => f.idFamiliar !== id);
            },
            error: (err) => console.error('Error eliminando familiar:', err)
        });
    }
}
