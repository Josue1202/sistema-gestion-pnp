import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface GradoPolicial {
    idGrado?: number;
    nombre: string;
    jerarquia: number;
    categoria: 'OFICIAL' | 'SUBOFICIAL' | 'CIVIL';
}

@Injectable({
    providedIn: 'root'
})
export class GradoService {
    private apiUrl = 'http://localhost:8080/api/grados';

    constructor(private http: HttpClient) { }

    getAll(): Observable<GradoPolicial[]> {
        return this.http.get<GradoPolicial[]>(this.apiUrl);
    }

    getById(id: number): Observable<GradoPolicial> {
        return this.http.get<GradoPolicial>(`${this.apiUrl}/${id}`);
    }

    create(grado: GradoPolicial): Observable<GradoPolicial> {
        return this.http.post<GradoPolicial>(this.apiUrl, grado);
    }

    update(id: number, grado: GradoPolicial): Observable<GradoPolicial> {
        return this.http.put<GradoPolicial>(`${this.apiUrl}/${id}`, grado);
    }

    delete(id: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/${id}`);
    }
}
