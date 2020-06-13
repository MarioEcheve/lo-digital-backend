import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IComuna, Comuna } from 'app/shared/model/comuna.model';
import { ComunaService } from './comuna.service';
import { ComunaComponent } from './comuna.component';
import { ComunaDetailComponent } from './comuna-detail.component';
import { ComunaUpdateComponent } from './comuna-update.component';

@Injectable({ providedIn: 'root' })
export class ComunaResolve implements Resolve<IComuna> {
  constructor(private service: ComunaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IComuna> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((comuna: HttpResponse<Comuna>) => {
          if (comuna.body) {
            return of(comuna.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Comuna());
  }
}

export const comunaRoute: Routes = [
  {
    path: '',
    component: ComunaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Comunas'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ComunaDetailComponent,
    resolve: {
      comuna: ComunaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Comunas'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ComunaUpdateComponent,
    resolve: {
      comuna: ComunaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Comunas'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ComunaUpdateComponent,
    resolve: {
      comuna: ComunaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Comunas'
    },
    canActivate: [UserRouteAccessService]
  }
];
