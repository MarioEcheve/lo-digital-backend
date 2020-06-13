import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFolio, Folio } from 'app/shared/model/folio.model';
import { FolioService } from './folio.service';
import { FolioComponent } from './folio.component';
import { FolioDetailComponent } from './folio-detail.component';
import { FolioUpdateComponent } from './folio-update.component';

@Injectable({ providedIn: 'root' })
export class FolioResolve implements Resolve<IFolio> {
  constructor(private service: FolioService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFolio> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((folio: HttpResponse<Folio>) => {
          if (folio.body) {
            return of(folio.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Folio());
  }
}

export const folioRoute: Routes = [
  {
    path: '',
    component: FolioComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Folios'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FolioDetailComponent,
    resolve: {
      folio: FolioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Folios'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FolioUpdateComponent,
    resolve: {
      folio: FolioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Folios'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FolioUpdateComponent,
    resolve: {
      folio: FolioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Folios'
    },
    canActivate: [UserRouteAccessService]
  }
];
