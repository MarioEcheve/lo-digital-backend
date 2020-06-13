import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITipoFirma, TipoFirma } from 'app/shared/model/tipo-firma.model';
import { TipoFirmaService } from './tipo-firma.service';
import { TipoFirmaComponent } from './tipo-firma.component';
import { TipoFirmaDetailComponent } from './tipo-firma-detail.component';
import { TipoFirmaUpdateComponent } from './tipo-firma-update.component';

@Injectable({ providedIn: 'root' })
export class TipoFirmaResolve implements Resolve<ITipoFirma> {
  constructor(private service: TipoFirmaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITipoFirma> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tipoFirma: HttpResponse<TipoFirma>) => {
          if (tipoFirma.body) {
            return of(tipoFirma.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TipoFirma());
  }
}

export const tipoFirmaRoute: Routes = [
  {
    path: '',
    component: TipoFirmaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TipoFirmas'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TipoFirmaDetailComponent,
    resolve: {
      tipoFirma: TipoFirmaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TipoFirmas'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TipoFirmaUpdateComponent,
    resolve: {
      tipoFirma: TipoFirmaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TipoFirmas'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TipoFirmaUpdateComponent,
    resolve: {
      tipoFirma: TipoFirmaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TipoFirmas'
    },
    canActivate: [UserRouteAccessService]
  }
];
