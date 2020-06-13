import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEstadoRespuesta, EstadoRespuesta } from 'app/shared/model/estado-respuesta.model';
import { EstadoRespuestaService } from './estado-respuesta.service';
import { EstadoRespuestaComponent } from './estado-respuesta.component';
import { EstadoRespuestaDetailComponent } from './estado-respuesta-detail.component';
import { EstadoRespuestaUpdateComponent } from './estado-respuesta-update.component';

@Injectable({ providedIn: 'root' })
export class EstadoRespuestaResolve implements Resolve<IEstadoRespuesta> {
  constructor(private service: EstadoRespuestaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEstadoRespuesta> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((estadoRespuesta: HttpResponse<EstadoRespuesta>) => {
          if (estadoRespuesta.body) {
            return of(estadoRespuesta.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EstadoRespuesta());
  }
}

export const estadoRespuestaRoute: Routes = [
  {
    path: '',
    component: EstadoRespuestaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'EstadoRespuestas'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EstadoRespuestaDetailComponent,
    resolve: {
      estadoRespuesta: EstadoRespuestaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'EstadoRespuestas'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EstadoRespuestaUpdateComponent,
    resolve: {
      estadoRespuesta: EstadoRespuestaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'EstadoRespuestas'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EstadoRespuestaUpdateComponent,
    resolve: {
      estadoRespuesta: EstadoRespuestaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'EstadoRespuestas'
    },
    canActivate: [UserRouteAccessService]
  }
];
