import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEstadoServicio, EstadoServicio } from 'app/shared/model/estado-servicio.model';
import { EstadoServicioService } from './estado-servicio.service';
import { EstadoServicioComponent } from './estado-servicio.component';
import { EstadoServicioDetailComponent } from './estado-servicio-detail.component';
import { EstadoServicioUpdateComponent } from './estado-servicio-update.component';

@Injectable({ providedIn: 'root' })
export class EstadoServicioResolve implements Resolve<IEstadoServicio> {
  constructor(private service: EstadoServicioService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEstadoServicio> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((estadoServicio: HttpResponse<EstadoServicio>) => {
          if (estadoServicio.body) {
            return of(estadoServicio.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EstadoServicio());
  }
}

export const estadoServicioRoute: Routes = [
  {
    path: '',
    component: EstadoServicioComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'EstadoServicios'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EstadoServicioDetailComponent,
    resolve: {
      estadoServicio: EstadoServicioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'EstadoServicios'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EstadoServicioUpdateComponent,
    resolve: {
      estadoServicio: EstadoServicioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'EstadoServicios'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EstadoServicioUpdateComponent,
    resolve: {
      estadoServicio: EstadoServicioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'EstadoServicios'
    },
    canActivate: [UserRouteAccessService]
  }
];
