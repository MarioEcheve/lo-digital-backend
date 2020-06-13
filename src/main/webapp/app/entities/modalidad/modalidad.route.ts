import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IModalidad, Modalidad } from 'app/shared/model/modalidad.model';
import { ModalidadService } from './modalidad.service';
import { ModalidadComponent } from './modalidad.component';
import { ModalidadDetailComponent } from './modalidad-detail.component';
import { ModalidadUpdateComponent } from './modalidad-update.component';

@Injectable({ providedIn: 'root' })
export class ModalidadResolve implements Resolve<IModalidad> {
  constructor(private service: ModalidadService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IModalidad> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((modalidad: HttpResponse<Modalidad>) => {
          if (modalidad.body) {
            return of(modalidad.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Modalidad());
  }
}

export const modalidadRoute: Routes = [
  {
    path: '',
    component: ModalidadComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Modalidads'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ModalidadDetailComponent,
    resolve: {
      modalidad: ModalidadResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Modalidads'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ModalidadUpdateComponent,
    resolve: {
      modalidad: ModalidadResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Modalidads'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ModalidadUpdateComponent,
    resolve: {
      modalidad: ModalidadResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Modalidads'
    },
    canActivate: [UserRouteAccessService]
  }
];
