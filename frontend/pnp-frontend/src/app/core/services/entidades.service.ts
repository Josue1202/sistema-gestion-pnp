import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

// ============================================
// INTERFACES (DTOs)
// ============================================

export interface AscensoDTO {
    idAscenso?: number;
    grado: string;
    rd?: string;
    fecha: string; // formato: yyyy-MM-dd
    motivo?: string;
}

export interface ServicioDTO {
    idServicio?: number;
    unidad: string;
    cargo?: string;
    desde: string; // formato: yyyy-MM-dd
    hasta?: string;
}

export interface CursoDTO {
    idCurso?: number;
    tipo: 'INSTITUCIONAL' | 'EXTRA';
    nombre: string;
    lugar?: string;
    fecha?: string; // formato: yyyy-MM-dd
}

export interface FamiliarDTO {
    idFamiliar?: number;
    nombresApellidos: string;
    fechaNac?: string; // formato: yyyy-MM-dd
    lugarNac?: string;
    parentesco: 'CONYUGE' | 'HIJO' | 'PADRE' | 'MADRE' | 'HERMANO';
}

// ============================================
// SERVICIOS
// ============================================

@Injectable({
    providedIn: 'root'
})
export class AscensoService {
    private apiUrl = 'http://localhost:8080/api/ascensos';

    constructor(private http: HttpClient) { }

    getByPersonal(idPersonal: number): Observable<AscensoDTO[]> {
        return this.http.get<AscensoDTO[]>(`${this.apiUrl}/personal/${idPersonal}`);
    }

    create(idPersonal: number, ascenso: AscensoDTO): Observable<AscensoDTO> {
        return this.http.post<AscensoDTO>(`${this.apiUrl}/personal/${idPersonal}`, ascenso);
    }

    delete(id: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/${id}`);
    }
}

@Injectable({
    providedIn: 'root'
})
export class ServicioService {
    private apiUrl = 'http://localhost:8080/api/servicios';

    constructor(private http: HttpClient) { }

    getByPersonal(idPersonal: number): Observable<ServicioDTO[]> {
        return this.http.get<ServicioDTO[]>(`${this.apiUrl}/personal/${idPersonal}`);
    }

    create(idPersonal: number, servicio: ServicioDTO): Observable<ServicioDTO> {
        return this.http.post<ServicioDTO>(`${this.apiUrl}/personal/${idPersonal}`, servicio);
    }

    delete(id: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/${id}`);
    }
}

@Injectable({
    providedIn: 'root'
})
export class CursoService {
    private apiUrl = 'http://localhost:8080/api/cursos';

    constructor(private http: HttpClient) { }

    getByPersonal(idPersonal: number): Observable<CursoDTO[]> {
        return this.http.get<CursoDTO[]>(`${this.apiUrl}/personal/${idPersonal}`);
    }

    create(idPersonal: number, curso: CursoDTO): Observable<CursoDTO> {
        return this.http.post<CursoDTO>(`${this.apiUrl}/personal/${idPersonal}`, curso);
    }

    delete(id: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/${id}`);
    }
}

@Injectable({
    providedIn: 'root'
})
export class FamiliarService {
    private apiUrl = 'http://localhost:8080/api/familiares';

    constructor(private http: HttpClient) { }

    getByPersonal(idPersonal: number): Observable<FamiliarDTO[]> {
        return this.http.get<FamiliarDTO[]>(`${this.apiUrl}/personal/${idPersonal}`);
    }

    create(idPersonal: number, familiar: FamiliarDTO): Observable<FamiliarDTO> {
        return this.http.post<FamiliarDTO>(`${this.apiUrl}/personal/${idPersonal}`, familiar);
    }

    delete(id: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/${id}`);
    }
}
