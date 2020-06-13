import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IArchivo, Archivo } from 'app/shared/model/archivo.model';
import { ArchivoService } from './archivo.service';
import { ArchivoComponent } from './archivo.component';
import { ArchivoDetailComponent } from './archivo-detail.component';
import { ArchivoUpdateComponent } from './archivo-update.component';

@Injectable({ providedIn: 'root' })
export class ArchivoResolve implements Resolve<IArchivo> {
  constructor(private service: ArchivoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IArchivo> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((archivo: HttpResponse<Archivo>) => {
          if (archivo.body) {
            return of(archivo.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Archivo());
  }
}

export const archivoRoute: Routes = [
  {
    path: '',
    component: ArchivoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Archivos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ArchivoDetailComponent,
    resolve: {
      archivo: ArchivoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Archivos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ArchivoUpdateComponent,
    resolve: {
      archivo: ArchivoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Archivos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ArchivoUpdateComponent,
    resolve: {
      archivo: ArchivoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Archivos'
    },
    canActivate: [UserRouteAccessService]
  }
];
