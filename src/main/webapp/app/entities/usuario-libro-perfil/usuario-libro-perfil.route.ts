import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUsuarioLibroPerfil, UsuarioLibroPerfil } from 'app/shared/model/usuario-libro-perfil.model';
import { UsuarioLibroPerfilService } from './usuario-libro-perfil.service';
import { UsuarioLibroPerfilComponent } from './usuario-libro-perfil.component';
import { UsuarioLibroPerfilDetailComponent } from './usuario-libro-perfil-detail.component';
import { UsuarioLibroPerfilUpdateComponent } from './usuario-libro-perfil-update.component';

@Injectable({ providedIn: 'root' })
export class UsuarioLibroPerfilResolve implements Resolve<IUsuarioLibroPerfil> {
  constructor(private service: UsuarioLibroPerfilService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUsuarioLibroPerfil> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((usuarioLibroPerfil: HttpResponse<UsuarioLibroPerfil>) => {
          if (usuarioLibroPerfil.body) {
            return of(usuarioLibroPerfil.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UsuarioLibroPerfil());
  }
}

export const usuarioLibroPerfilRoute: Routes = [
  {
    path: '',
    component: UsuarioLibroPerfilComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'UsuarioLibroPerfils'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: UsuarioLibroPerfilDetailComponent,
    resolve: {
      usuarioLibroPerfil: UsuarioLibroPerfilResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'UsuarioLibroPerfils'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: UsuarioLibroPerfilUpdateComponent,
    resolve: {
      usuarioLibroPerfil: UsuarioLibroPerfilResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'UsuarioLibroPerfils'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: UsuarioLibroPerfilUpdateComponent,
    resolve: {
      usuarioLibroPerfil: UsuarioLibroPerfilResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'UsuarioLibroPerfils'
    },
    canActivate: [UserRouteAccessService]
  }
];
