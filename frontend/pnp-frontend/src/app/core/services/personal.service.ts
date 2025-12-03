import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PersonalPNP, FuncionPolicial } from '../models/models';

@Injectable({
    providedIn: 'root'
})
export class PersonalService {
    private apiUrl = 'http://localhost:8080/api/personal';
    private funcionesUrl = 'http://localhost:8080/api/funciones';

    constructor(private http: HttpClient) { }

    // Personal CRUD
    getAllPersonal(): Observable<PersonalPNP[]> {
        return this.http.get<PersonalPNP[]>(this.apiUrl);
    }

    getPersonalById(id: number): Observable<PersonalPNP> {
        return this.http.get<PersonalPNP>(`${this.apiUrl}/${id}`);
    }

    getPersonalByCip(cip: string): Observable<PersonalPNP> {
        return this.http.get<PersonalPNP>(`${this.apiUrl}/cip/${cip}`);
    }

    getPersonalByDni(dni: string): Observable<PersonalPNP> {
        return this.http.get<PersonalPNP>(`${this.apiUrl}/dni/${dni}`);
    }

    getPersonalByUnidad(unidad: string): Observable<PersonalPNP[]> {
        return this.http.get<PersonalPNP[]>(`${this.apiUrl}/unidad/${unidad}`);
    }

    searchPersonal(query: string): Observable<PersonalPNP[]> {
        const params = new HttpParams().set('q', query);
        return this.http.get<PersonalPNP[]>(`${this.apiUrl}/search`, { params });
    }

    createPersonal(personal: PersonalPNP): Observable<PersonalPNP> {
        return this.http.post<PersonalPNP>(this.apiUrl, personal);
    }

    updatePersonal(id: number, personal: PersonalPNP): Observable<PersonalPNP> {
        return this.http.put<PersonalPNP>(`${this.apiUrl}/${id}`, personal);
    }

    deletePersonal(id: number): Observable<any> {
        return this.http.delete(`${this.apiUrl}/${id}`);
    }

    // Estadísticas
    getStats(): Observable<any> {
        return this.http.get<any>(`${this.apiUrl}/stats`);
    }

    getStatsPorGrado(): Observable<any[]> {
        return this.http.get<any[]>(`${this.apiUrl}/stats/por-grado`);
    }

    getStatsPorUnidad(): Observable<any[]> {
        return this.http.get<any[]>(`${this.apiUrl}/stats/por-unidad`);
    }

    // Funciones
    getFuncionesByPersonalId(personalId: number): Observable<FuncionPolicial[]> {
        return this.http.get<FuncionPolicial[]>(`${this.funcionesUrl}/personal/${personalId}`);
    }

    createFuncion(personalId: number, funcion: FuncionPolicial): Observable<FuncionPolicial> {
        return this.http.post<FuncionPolicial>(`${this.funcionesUrl}/personal/${personalId}`, funcion);
    }

    updateFuncion(id: number, funcion: FuncionPolicial): Observable<FuncionPolicial> {
        return this.http.put<FuncionPolicial>(`${this.funcionesUrl}/${id}`, funcion);
    }

    deleteFuncion(id: number): Observable<any> {
        return this.http.delete(`${this.funcionesUrl}/${id}`);
    }
}
