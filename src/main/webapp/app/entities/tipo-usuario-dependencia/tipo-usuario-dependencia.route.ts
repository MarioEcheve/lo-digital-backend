import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITipoUsuarioDependencia, TipoUsuarioDependencia } from 'app/shared/model/tipo-usuario-dependencia.model';
import { TipoUsuarioDependenciaService } from './tipo-usuario-dependencia.service';
import { TipoUsuarioDependenciaComponent } from './tipo-usuario-dependencia.component';
import { TipoUsuarioDependenciaDetailComponent } from './tipo-usuario-dependencia-detail.component';
import { TipoUsuarioDependenciaUpdateComponent } from './tipo-usuario-dependencia-update.component';

@Injectable({ providedIn: 'root' })
export class TipoUsuarioDependenciaResolve implements Resolve<ITipoUsuarioDependencia> {
  constructor(private service: TipoUsuarioDependenciaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITipoUsuarioDependencia> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tipoUsuarioDependencia: HttpResponse<TipoUsuarioDependencia>) => {
          if (tipoUsuarioDependencia.body) {
            return of(tipoUsuarioDependencia.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TipoUsuarioDependencia());
  }
}

export const tipoUsuarioDependenciaRoute: Routes = [
  {
    path: '',
    component: TipoUsuarioDependenciaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TipoUsuarioDependencias'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TipoUsuarioDependenciaDetailComponent,
    resolve: {
      tipoUsuarioDependencia: TipoUsuarioDependenciaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TipoUsuarioDependencias'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TipoUsuarioDependenciaUpdateComponent,
    resolve: {
      tipoUsuarioDependencia: TipoUsuarioDependenciaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TipoUsuarioDependencias'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TipoUsuarioDependenciaUpdateComponent,
    resolve: {
      tipoUsuarioDependencia: TipoUsuarioDependenciaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TipoUsuarioDependencias'
    },
    canActivate: [UserRouteAccessService]
  }
];
