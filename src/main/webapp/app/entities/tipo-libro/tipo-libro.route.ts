import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITipoLibro, TipoLibro } from 'app/shared/model/tipo-libro.model';
import { TipoLibroService } from './tipo-libro.service';
import { TipoLibroComponent } from './tipo-libro.component';
import { TipoLibroDetailComponent } from './tipo-libro-detail.component';
import { TipoLibroUpdateComponent } from './tipo-libro-update.component';

@Injectable({ providedIn: 'root' })
export class TipoLibroResolve implements Resolve<ITipoLibro> {
  constructor(private service: TipoLibroService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITipoLibro> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tipoLibro: HttpResponse<TipoLibro>) => {
          if (tipoLibro.body) {
            return of(tipoLibro.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TipoLibro());
  }
}

export const tipoLibroRoute: Routes = [
  {
    path: '',
    component: TipoLibroComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TipoLibros'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TipoLibroDetailComponent,
    resolve: {
      tipoLibro: TipoLibroResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TipoLibros'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TipoLibroUpdateComponent,
    resolve: {
      tipoLibro: TipoLibroResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TipoLibros'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TipoLibroUpdateComponent,
    resolve: {
      tipoLibro: TipoLibroResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TipoLibros'
    },
    canActivate: [UserRouteAccessService]
  }
];
