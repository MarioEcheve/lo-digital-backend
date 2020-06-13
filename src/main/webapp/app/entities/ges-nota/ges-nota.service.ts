import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IGesNota } from 'app/shared/model/ges-nota.model';

type EntityResponseType = HttpResponse<IGesNota>;
type EntityArrayResponseType = HttpResponse<IGesNota[]>;

@Injectable({ providedIn: 'root' })
export class GesNotaService {
  public resourceUrl = SERVER_API_URL + 'api/ges-notas';

  constructor(protected http: HttpClient) {}

  create(gesNota: IGesNota): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(gesNota);
    return this.http
      .post<IGesNota>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(gesNota: IGesNota): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(gesNota);
    return this.http
      .put<IGesNota>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IGesNota>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IGesNota[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(gesNota: IGesNota): IGesNota {
    const copy: IGesNota = Object.assign({}, gesNota, {
      fechaCreacion: gesNota.fechaCreacion && gesNota.fechaCreacion.isValid() ? gesNota.fechaCreacion.toJSON() : undefined,
      fechaModificacion: gesNota.fechaModificacion && gesNota.fechaModificacion.isValid() ? gesNota.fechaModificacion.toJSON() : undefined
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
      res.body.forEach((gesNota: IGesNota) => {
        gesNota.fechaCreacion = gesNota.fechaCreacion ? moment(gesNota.fechaCreacion) : undefined;
        gesNota.fechaModificacion = gesNota.fechaModificacion ? moment(gesNota.fechaModificacion) : undefined;
      });
    }
    return res;
  }
}
