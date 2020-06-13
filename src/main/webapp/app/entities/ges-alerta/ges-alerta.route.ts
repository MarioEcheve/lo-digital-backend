import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IGesAlerta, GesAlerta } from 'app/shared/model/ges-alerta.model';
import { GesAlertaService } from './ges-alerta.service';
import { GesAlertaComponent } from './ges-alerta.component';
import { GesAlertaDetailComponent } from './ges-alerta-detail.component';
import { GesAlertaUpdateComponent } from './ges-alerta-update.component';

@Injectable({ providedIn: 'root' })
export class GesAlertaResolve implements Resolve<IGesAlerta> {
  constructor(private service: GesAlertaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGesAlerta> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((gesAlerta: HttpResponse<GesAlerta>) => {
          if (gesAlerta.body) {
            return of(gesAlerta.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new GesAlerta());
  }
}

export const gesAlertaRoute: Routes = [
  {
    path: '',
    component: GesAlertaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'GesAlertas'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: GesAlertaDetailComponent,
    resolve: {
      gesAlerta: GesAlertaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'GesAlertas'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: GesAlertaUpdateComponent,
    resolve: {
      gesAlerta: GesAlertaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'GesAlertas'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: GesAlertaUpdateComponent,
    resolve: {
      gesAlerta: GesAlertaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'GesAlertas'
    },
    canActivate: [UserRouteAccessService]
  }
];
