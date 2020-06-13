import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITipoEntidad, TipoEntidad } from 'app/shared/model/tipo-entidad.model';
import { TipoEntidadService } from './tipo-entidad.service';
import { TipoEntidadComponent } from './tipo-entidad.component';
import { TipoEntidadDetailComponent } from './tipo-entidad-detail.component';
import { TipoEntidadUpdateComponent } from './tipo-entidad-update.component';

@Injectable({ providedIn: 'root' })
export class TipoEntidadResolve implements Resolve<ITipoEntidad> {
  constructor(private service: TipoEntidadService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITipoEntidad> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tipoEntidad: HttpResponse<TipoEntidad>) => {
          if (tipoEntidad.body) {
            return of(tipoEntidad.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TipoEntidad());
  }
}

export const tipoEntidadRoute: Routes = [
  {
    path: '',
    component: TipoEntidadComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TipoEntidads'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TipoEntidadDetailComponent,
    resolve: {
      tipoEntidad: TipoEntidadResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TipoEntidads'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TipoEntidadUpdateComponent,
    resolve: {
      tipoEntidad: TipoEntidadResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TipoEntidads'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TipoEntidadUpdateComponent,
    resolve: {
      tipoEntidad: TipoEntidadResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TipoEntidads'
    },
    canActivate: [UserRouteAccessService]
  }
];
