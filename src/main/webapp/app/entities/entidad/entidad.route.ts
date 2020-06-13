import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEntidad, Entidad } from 'app/shared/model/entidad.model';
import { EntidadService } from './entidad.service';
import { EntidadComponent } from './entidad.component';
import { EntidadDetailComponent } from './entidad-detail.component';
import { EntidadUpdateComponent } from './entidad-update.component';

@Injectable({ providedIn: 'root' })
export class EntidadResolve implements Resolve<IEntidad> {
  constructor(private service: EntidadService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEntidad> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((entidad: HttpResponse<Entidad>) => {
          if (entidad.body) {
            return of(entidad.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Entidad());
  }
}

export const entidadRoute: Routes = [
  {
    path: '',
    component: EntidadComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Entidads'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EntidadDetailComponent,
    resolve: {
      entidad: EntidadResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Entidads'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EntidadUpdateComponent,
    resolve: {
      entidad: EntidadResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Entidads'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EntidadUpdateComponent,
    resolve: {
      entidad: EntidadResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Entidads'
    },
    canActivate: [UserRouteAccessService]
  }
];
