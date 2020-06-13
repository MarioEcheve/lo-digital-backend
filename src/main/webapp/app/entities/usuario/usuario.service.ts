import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUsuario } from 'app/shared/model/usuario.model';

type EntityResponseType = HttpResponse<IUsuario>;
type EntityArrayResponseType = HttpResponse<IUsuario[]>;

@Injectable({ providedIn: 'root' })
export class UsuarioService {
  public resourceUrl = SERVER_API_URL + 'api/usuarios';

  constructor(protected http: HttpClient) {}

  create(usuario: IUsuario): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(usuario);
    return this.http
      .post<IUsuario>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(usuario: IUsuario): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(usuario);
    return this.http
      .put<IUsuario>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IUsuario>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IUsuario[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(usuario: IUsuario): IUsuario {
    const copy: IUsuario = Object.assign({}, usuario, {
      fechaCreacion: usuario.fechaCreacion && usuario.fechaCreacion.isValid() ? usuario.fechaCreacion.toJSON() : undefined,
      fechaModificacion: usuario.fechaModificacion && usuario.fechaModificacion.isValid() ? usuario.fechaModificacion.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaCreacion = res.body.fechaCreacion ? moment(res.body.fechaCreacion) : undefined;
      res.body.fechaModificacion = res.body.fechaModificacion ? moment(res.body.fechaModificacion) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((usuario: IUsuario) => {
        usuario.fechaCreacion = usuario.fechaCreacion ? moment(usuario.fechaCreacion) : undefined;
        usuario.fechaModificacion = usuario.fechaModificacion ? moment(usuario.fechaModificacion) : undefined;
      });
    }
    return res;
  }
}
