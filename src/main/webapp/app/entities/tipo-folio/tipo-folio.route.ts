import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITipoFolio, TipoFolio } from 'app/shared/model/tipo-folio.model';
import { TipoFolioService } from './tipo-folio.service';
import { TipoFolioComponent } from './tipo-folio.component';
import { TipoFolioDetailComponent } from './tipo-folio-detail.component';
import { TipoFolioUpdateComponent } from './tipo-folio-update.component';

@Injectable({ providedIn: 'root' })
export class TipoFolioResolve implements Resolve<ITipoFolio> {
  constructor(private service: TipoFolioService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITipoFolio> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tipoFolio: HttpResponse<TipoFolio>) => {
          if (tipoFolio.body) {
            return of(tipoFolio.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TipoFolio());
  }
}

export const tipoFolioRoute: Routes = [
  {
    path: '',
    component: TipoFolioComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TipoFolios'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TipoFolioDetailComponent,
    resolve: {
      tipoFolio: TipoFolioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TipoFolios'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TipoFolioUpdateComponent,
    resolve: {
      tipoFolio: TipoFolioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TipoFolios'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TipoFolioUpdateComponent,
    resolve: {
      tipoFolio: TipoFolioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TipoFolios'
    },
    canActivate: [UserRouteAccessService]
  }
];
