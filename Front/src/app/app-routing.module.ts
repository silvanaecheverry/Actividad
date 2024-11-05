import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './estudiante/login/login.component';
import { OpcionesEvaluacionComponent } from './estudiante/opciones-evaluacion/opciones-evaluacion.component';

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' }, // Redirect to login on app load
  { path: 'login', component: LoginComponent },
  { path: 'opciones' , component: OpcionesEvaluacionComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
