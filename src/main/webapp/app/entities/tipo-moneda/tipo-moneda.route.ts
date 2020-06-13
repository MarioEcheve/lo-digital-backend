import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITipoMoneda, TipoMoneda } from 'app/shared/model/tipo-moneda.model';
import { TipoMonedaService } from './tipo-moneda.service';
import { TipoMonedaComponent } from './tipo-moneda.component';
import { TipoMonedaDetailComponent } from './tipo-moneda-detail.component';
import { TipoMonedaUpdateComponent } from './tipo-moneda-update.component';

@Injectable({ providedIn: 'root' })
export class TipoMonedaResolve implements Resolve<ITipoMoneda> {
  constructor(private service: TipoMonedaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITipoMoneda> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tipoMoneda: HttpResponse<TipoMoneda>) => {
          if (tipoMoneda.body) {
            return of(tipoMoneda.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TipoMoneda());
  }
}

export const tipoMonedaRoute: Routes = [
  {
    path: '',
    component: TipoMonedaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TipoMonedas'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TipoMonedaDetailComponent,
    resolve: {
      tipoMoneda: TipoMonedaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TipoMonedas'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TipoMonedaUpdateComponent,
    resolve: {
      tipoMoneda: TipoMonedaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TipoMonedas'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TipoMonedaUpdateComponent,
    resolve: {
      tipoMoneda: TipoMonedaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TipoMonedas'
    },
    canActivate: [UserRouteAccessService]
  }
];
