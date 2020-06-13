import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUsuarioLibro, UsuarioLibro } from 'app/shared/model/usuario-libro.model';
import { UsuarioLibroService } from './usuario-libro.service';
import { UsuarioLibroComponent } from './usuario-libro.component';
import { UsuarioLibroDetailComponent } from './usuario-libro-detail.component';
import { UsuarioLibroUpdateComponent } from './usuario-libro-update.component';

@Injectable({ providedIn: 'root' })
export class UsuarioLibroResolve implements Resolve<IUsuarioLibro> {
  constructor(private service: UsuarioLibroService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUsuarioLibro> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((usuarioLibro: HttpResponse<UsuarioLibro>) => {
          if (usuarioLibro.body) {
            return of(usuarioLibro.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UsuarioLibro());
  }
}

export const usuarioLibroRoute: Routes = [
  {
    path: '',
    component: UsuarioLibroComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'UsuarioLibros'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: UsuarioLibroDetailComponent,
    resolve: {
      usuarioLibro: UsuarioLibroResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'UsuarioLibros'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: UsuarioLibroUpdateComponent,
    resolve: {
      usuarioLibro: UsuarioLibroResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'UsuarioLibros'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: UsuarioLibroUpdateComponent,
    resolve: {
      usuarioLibro: UsuarioLibroResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'UsuarioLibros'
    },
    canActivate: [UserRouteAccessService]
  }
];
