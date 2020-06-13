import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEstadoLibro, EstadoLibro } from 'app/shared/model/estado-libro.model';
import { EstadoLibroService } from './estado-libro.service';
import { EstadoLibroComponent } from './estado-libro.component';
import { EstadoLibroDetailComponent } from './estado-libro-detail.component';
import { EstadoLibroUpdateComponent } from './estado-libro-update.component';

@Injectable({ providedIn: 'root' })
export class EstadoLibroResolve implements Resolve<IEstadoLibro> {
  constructor(private service: EstadoLibroService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEstadoLibro> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((estadoLibro: HttpResponse<EstadoLibro>) => {
          if (estadoLibro.body) {
            return of(estadoLibro.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EstadoLibro());
  }
}

export const estadoLibroRoute: Routes = [
  {
    path: '',
    component: EstadoLibroComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'EstadoLibros'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EstadoLibroDetailComponent,
    resolve: {
      estadoLibro: EstadoLibroResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'EstadoLibros'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EstadoLibroUpdateComponent,
    resolve: {
      estadoLibro: EstadoLibroResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'EstadoLibros'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EstadoLibroUpdateComponent,
    resolve: {
      estadoLibro: EstadoLibroResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'EstadoLibros'
    },
    canActivate: [UserRouteAccessService]
  }
];
