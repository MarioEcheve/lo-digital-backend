import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IContrato, Contrato } from 'app/shared/model/contrato.model';
import { ContratoService } from './contrato.service';
import { ContratoComponent } from './contrato.component';
import { ContratoDetailComponent } from './contrato-detail.component';
import { ContratoUpdateComponent } from './contrato-update.component';

@Injectable({ providedIn: 'root' })
export class ContratoResolve implements Resolve<IContrato> {
  constructor(private service: ContratoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IContrato> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((contrato: HttpResponse<Contrato>) => {
          if (contrato.body) {
            return of(contrato.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Contrato());
  }
}

export const contratoRoute: Routes = [
  {
    path: '',
    component: ContratoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Contratoes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ContratoDetailComponent,
    resolve: {
      contrato: ContratoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Contratoes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ContratoUpdateComponent,
    resolve: {
      contrato: ContratoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Contratoes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ContratoUpdateComponent,
    resolve: {
      contrato: ContratoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Contratoes'
    },
    canActivate: [UserRouteAccessService]
  }
];
