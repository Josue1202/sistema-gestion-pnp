import { Component, OnInit, AfterViewInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Chart, registerables } from 'chart.js';
import { PersonalService } from '../../core/services/personal.service';

Chart.register(...registerables);

@Component({
    selector: 'app-dashboard',
    standalone: true,
    imports: [CommonModule],
    templateUrl: './dashboard.component.html',
    styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit, AfterViewInit {

    stats: any = null;
    loading = true;

    private gradoChart: any;
    private unidadChart: any;

    constructor(private personalService: PersonalService) { }

    ngOnInit(): void {
        this.loadStats();
    }

    ngAfterViewInit(): void {
        setTimeout(() => {
            this.createCharts();
        }, 100);
    }

    loadStats(): void {
        this.personalService.getStats().subscribe({
            next: (data) => {
                this.stats = data;
                this.loading = false;
            },
            error: (err) => console.error('Error cargando stats:', err)
        });
    }

    createCharts(): void {
        this.createGradoChart();
        this.createUnidadChart();
    }

    createGradoChart(): void {
        this.personalService.getStatsPorGrado().subscribe({
            next: (data: any[]) => {
                const labels = data.map(item => item.nombre);
                const values = data.map(item => item.cantidad);

                const ctx = document.getElementById('gradoChart') as HTMLCanvasElement;
                if (ctx) {
                    this.gradoChart = new Chart(ctx, {
                        type: 'pie',
                        data: {
                            labels: labels,
                            datasets: [{
                                label: 'Personal por Grado',
                                data: values,
                                backgroundColor: [
                                    'rgba(59, 130, 246, 0.8)',
                                    'rgba(16, 185, 129, 0.8)',
                                    'rgba(139, 92, 246, 0.8)',
                                    'rgba(245, 158, 11, 0.8)',
                                    'rgba(239, 68, 68, 0.8)',
                                    'rgba(236, 72, 153, 0.8)'
                                ],
                                borderColor: 'rgba(255, 255, 255, 0.2)',
                                borderWidth: 2
                            }]
                        },
                        options: {
                            responsive: true,
                            maintainAspectRatio: false,
                            plugins: {
                                legend: {
                                    position: 'bottom',
                                    labels: {
                                        color: '#e5e7eb',
                                        font: { size: 12 }
                                    }
                                },
                                title: {
                                    display: true,
                                    text: 'Personal por Grado',
                                    color: '#f3f4f6',
                                    font: { size: 16, weight: 'bold' }
                                }
                            }
                        }
                    });
                }
            },
            error: (err) => console.error('Error cargando grados:', err)
        });
    }

    createUnidadChart(): void {
        this.personalService.getStatsPorUnidad().subscribe({
            next: (data: any[]) => {
                const labels = data.map(item => item.nombre);
                const values = data.map(item => item.cantidad);

                const ctx = document.getElementById('unidadChart') as HTMLCanvasElement;
                if (ctx) {
                    this.unidadChart = new Chart(ctx, {
                        type: 'bar',
                        data: {
                            labels: labels,
                            datasets: [{
                                label: 'Efectivos',
                                data: values,
                                backgroundColor: 'rgba(59, 130, 246, 0.8)',
                                borderColor: 'rgba(59, 130, 246, 1)',
                                borderWidth: 2,
                                borderRadius: 8
                            }]
                        },
                        options: {
                            responsive: true,
                            maintainAspectRatio: false,
                            plugins: {
                                legend: {
                                    display: false
                                },
                                title: {
                                    display: true,
                                    text: 'Personal por Unidad',
                                    color: '#f3f4f6',
                                    font: { size: 16, weight: 'bold' }
                                }
                            },
                            scales: {
                                y: {
                                    beginAtZero: true,
                                    ticks: { color: '#9ca3af' },
                                    grid: { color: 'rgba(255, 255, 255, 0.05)' }
                                },
                                x: {
                                    ticks: {
                                        color: '#9ca3af',
                                        maxRotation: 45,
                                        minRotation: 45
                                    },
                                    grid: { display: false }
                                }
                            }
                        }
                    });
                }
            },
            error: (err) => console.error('Error cargando unidades:', err)
        });
    }

    ngOnDestroy(): void {
        if (this.gradoChart) this.gradoChart.destroy();
        if (this.unidadChart) this.unidadChart.destroy();
    }
}
