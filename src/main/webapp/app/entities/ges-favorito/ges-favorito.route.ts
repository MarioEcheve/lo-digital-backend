import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IGesFavorito, GesFavorito } from 'app/shared/model/ges-favorito.model';
import { GesFavoritoService } from './ges-favorito.service';
import { GesFavoritoComponent } from './ges-favorito.component';
import { GesFavoritoDetailComponent } from './ges-favorito-detail.component';
import { GesFavoritoUpdateComponent } from './ges-favorito-update.component';

@Injectable({ providedIn: 'root' })
export class GesFavoritoResolve implements Resolve<IGesFavorito> {
  constructor(private service: GesFavoritoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGesFavorito> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((gesFavorito: HttpResponse<GesFavorito>) => {
          if (gesFavorito.body) {
            return of(gesFavorito.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new GesFavorito());
  }
}

export const gesFavoritoRoute: Routes = [
  {
    path: '',
    component: GesFavoritoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'GesFavoritos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: GesFavoritoDetailComponent,
    resolve: {
      gesFavorito: GesFavoritoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'GesFavoritos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: GesFavoritoUpdateComponent,
    resolve: {
      gesFavorito: GesFavoritoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'GesFavoritos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: GesFavoritoUpdateComponent,
    resolve: {
      gesFavorito: GesFavoritoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'GesFavoritos'
    },
    canActivate: [UserRouteAccessService]
  }
];
