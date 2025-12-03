import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface UnidadPolicial {
    idUnidad?: number;
    nombre: string;
    siglas?: string;
    tipo: 'COMISARIA' | 'DIVISION' | 'REGION' | 'ADMINISTRATIVO' | 'ESPECIAL';
}

@Injectable({
    providedIn: 'root'
})
export class UnidadService {
    private apiUrl = 'http://localhost:8080/api/unidades';

    constructor(private http: HttpClient) { }

    getAll(): Observable<UnidadPolicial[]> {
        return this.http.get<UnidadPolicial[]>(this.apiUrl);
    }

    getById(id: number): Observable<UnidadPolicial> {
        return this.http.get<UnidadPolicial>(`${this.apiUrl}/${id}`);
    }

    create(unidad: UnidadPolicial): Observable<UnidadPolicial> {
        return this.http.post<UnidadPolicial>(this.apiUrl, unidad);
    }

    update(id: number, unidad: UnidadPolicial): Observable<UnidadPolicial> {
        return this.http.put<UnidadPolicial>(`${this.apiUrl}/${id}`, unidad);
    }

    delete(id: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/${id}`);
    }
}
