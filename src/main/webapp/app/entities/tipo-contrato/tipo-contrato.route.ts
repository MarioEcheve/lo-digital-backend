import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITipoContrato, TipoContrato } from 'app/shared/model/tipo-contrato.model';
import { TipoContratoService } from './tipo-contrato.service';
import { TipoContratoComponent } from './tipo-contrato.component';
import { TipoContratoDetailComponent } from './tipo-contrato-detail.component';
import { TipoContratoUpdateComponent } from './tipo-contrato-update.component';

@Injectable({ providedIn: 'root' })
export class TipoContratoResolve implements Resolve<ITipoContrato> {
  constructor(private service: TipoContratoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITipoContrato> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tipoContrato: HttpResponse<TipoContrato>) => {
          if (tipoContrato.body) {
            return of(tipoContrato.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TipoContrato());
  }
}

export const tipoContratoRoute: Routes = [
  {
    path: '',
    component: TipoContratoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TipoContratoes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TipoContratoDetailComponent,
    resolve: {
      tipoContrato: TipoContratoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TipoContratoes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TipoContratoUpdateComponent,
    resolve: {
      tipoContrato: TipoContratoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TipoContratoes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TipoContratoUpdateComponent,
    resolve: {
      tipoContrato: TipoContratoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TipoContratoes'
    },
    canActivate: [UserRouteAccessService]
  }
];
