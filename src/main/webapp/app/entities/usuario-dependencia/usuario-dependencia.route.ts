import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUsuarioDependencia, UsuarioDependencia } from 'app/shared/model/usuario-dependencia.model';
import { UsuarioDependenciaService } from './usuario-dependencia.service';
import { UsuarioDependenciaComponent } from './usuario-dependencia.component';
import { UsuarioDependenciaDetailComponent } from './usuario-dependencia-detail.component';
import { UsuarioDependenciaUpdateComponent } from './usuario-dependencia-update.component';

@Injectable({ providedIn: 'root' })
export class UsuarioDependenciaResolve implements Resolve<IUsuarioDependencia> {
  constructor(private service: UsuarioDependenciaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUsuarioDependencia> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((usuarioDependencia: HttpResponse<UsuarioDependencia>) => {
          if (usuarioDependencia.body) {
            return of(usuarioDependencia.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UsuarioDependencia());
  }
}

export const usuarioDependenciaRoute: Routes = [
  {
    path: '',
    component: UsuarioDependenciaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'UsuarioDependencias'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: UsuarioDependenciaDetailComponent,
    resolve: {
      usuarioDependencia: UsuarioDependenciaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'UsuarioDependencias'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: UsuarioDependenciaUpdateComponent,
    resolve: {
      usuarioDependencia: UsuarioDependenciaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'UsuarioDependencias'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: UsuarioDependenciaUpdateComponent,
    resolve: {
      usuarioDependencia: UsuarioDependenciaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'UsuarioDependencias'
    },
    canActivate: [UserRouteAccessService]
  }
];
