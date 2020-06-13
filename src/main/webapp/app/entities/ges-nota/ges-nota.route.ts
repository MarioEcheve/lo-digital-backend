import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IGesNota, GesNota } from 'app/shared/model/ges-nota.model';
import { GesNotaService } from './ges-nota.service';
import { GesNotaComponent } from './ges-nota.component';
import { GesNotaDetailComponent } from './ges-nota-detail.component';
import { GesNotaUpdateComponent } from './ges-nota-update.component';

@Injectable({ providedIn: 'root' })
export class GesNotaResolve implements Resolve<IGesNota> {
  constructor(private service: GesNotaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGesNota> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((gesNota: HttpResponse<GesNota>) => {
          if (gesNota.body) {
            return of(gesNota.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new GesNota());
  }
}

export const gesNotaRoute: Routes = [
  {
    path: '',
    component: GesNotaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'GesNotas'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: GesNotaDetailComponent,
    resolve: {
      gesNota: GesNotaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'GesNotas'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: GesNotaUpdateComponent,
    resolve: {
      gesNota: GesNotaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'GesNotas'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: GesNotaUpdateComponent,
    resolve: {
      gesNota: GesNotaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'GesNotas'
    },
    canActivate: [UserRouteAccessService]
  }
];
