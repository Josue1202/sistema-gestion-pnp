export interface PersonalPNP {
    id?: number;
    cip: string;
    dni: string;
    apellidoPaterno: string;
    apellidoMaterno: string;
    nombres: string;
    fechaNacimiento: string;
    genero: string;
    telefono?: string;
    email?: string;
    direccion?: string;
    rango: string;
    especialidad?: string;
    unidad: string;
    estado: string;
    fechaIngreso: string;
    fotoUrl?: string;
    createdAt?: string;
    updatedAt?: string;
}

export interface FuncionPolicial {
    id?: number;
    funcion: string;
    fechaAsignacion: string;
    fechaFin?: string;
    activo: boolean;
}

export interface Usuario {
    id: number;
    username: string;
    cip?: string;
    rol: string;
    activo: boolean;
}

export interface LoginRequest {
    username: string;
    password: string;
}

export interface LoginResponse {
    token: string;
    type: string;
    id: number;
    username: string;
    rol: string;
    cip?: string;
}
