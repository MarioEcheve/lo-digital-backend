import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITipoMonto, TipoMonto } from 'app/shared/model/tipo-monto.model';
import { TipoMontoService } from './tipo-monto.service';
import { TipoMontoComponent } from './tipo-monto.component';
import { TipoMontoDetailComponent } from './tipo-monto-detail.component';
import { TipoMontoUpdateComponent } from './tipo-monto-update.component';

@Injectable({ providedIn: 'root' })
export class TipoMontoResolve implements Resolve<ITipoMonto> {
  constructor(private service: TipoMontoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITipoMonto> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tipoMonto: HttpResponse<TipoMonto>) => {
          if (tipoMonto.body) {
            return of(tipoMonto.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TipoMonto());
  }
}

export const tipoMontoRoute: Routes = [
  {
    path: '',
    component: TipoMontoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TipoMontos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TipoMontoDetailComponent,
    resolve: {
      tipoMonto: TipoMontoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TipoMontos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TipoMontoUpdateComponent,
    resolve: {
      tipoMonto: TipoMontoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TipoMontos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TipoMontoUpdateComponent,
    resolve: {
      tipoMonto: TipoMontoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TipoMontos'
    },
    canActivate: [UserRouteAccessService]
  }
];
