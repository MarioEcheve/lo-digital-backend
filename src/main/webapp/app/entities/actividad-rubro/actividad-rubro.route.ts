import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IActividadRubro, ActividadRubro } from 'app/shared/model/actividad-rubro.model';
import { ActividadRubroService } from './actividad-rubro.service';
import { ActividadRubroComponent } from './actividad-rubro.component';
import { ActividadRubroDetailComponent } from './actividad-rubro-detail.component';
import { ActividadRubroUpdateComponent } from './actividad-rubro-update.component';

@Injectable({ providedIn: 'root' })
export class ActividadRubroResolve implements Resolve<IActividadRubro> {
  constructor(private service: ActividadRubroService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IActividadRubro> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((actividadRubro: HttpResponse<ActividadRubro>) => {
          if (actividadRubro.body) {
            return of(actividadRubro.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ActividadRubro());
  }
}

export const actividadRubroRoute: Routes = [
  {
    path: '',
    component: ActividadRubroComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ActividadRubros'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ActividadRubroDetailComponent,
    resolve: {
      actividadRubro: ActividadRubroResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ActividadRubros'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ActividadRubroUpdateComponent,
    resolve: {
      actividadRubro: ActividadRubroResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ActividadRubros'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ActividadRubroUpdateComponent,
    resolve: {
      actividadRubro: ActividadRubroResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ActividadRubros'
    },
    canActivate: [UserRouteAccessService]
  }
];
