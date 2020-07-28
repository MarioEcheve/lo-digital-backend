import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFolioReferencia, FolioReferencia } from 'app/shared/model/folio-referencia.model';
import { FolioReferenciaService } from './folio-referencia.service';
import { FolioReferenciaComponent } from './folio-referencia.component';
import { FolioReferenciaDetailComponent } from './folio-referencia-detail.component';
import { FolioReferenciaUpdateComponent } from './folio-referencia-update.component';

@Injectable({ providedIn: 'root' })
export class FolioReferenciaResolve implements Resolve<IFolioReferencia> {
  constructor(private service: FolioReferenciaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFolioReferencia> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((folioReferencia: HttpResponse<FolioReferencia>) => {
          if (folioReferencia.body) {
            return of(folioReferencia.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FolioReferencia());
  }
}

export const folioReferenciaRoute: Routes = [
  {
    path: '',
    component: FolioReferenciaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'FolioReferencias'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FolioReferenciaDetailComponent,
    resolve: {
      folioReferencia: FolioReferenciaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'FolioReferencias'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FolioReferenciaUpdateComponent,
    resolve: {
      folioReferencia: FolioReferenciaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'FolioReferencias'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FolioReferenciaUpdateComponent,
    resolve: {
      folioReferencia: FolioReferenciaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'FolioReferencias'
    },
    canActivate: [UserRouteAccessService]
  }
];
