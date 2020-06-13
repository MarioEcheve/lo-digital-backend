import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IGesAlerta } from 'app/shared/model/ges-alerta.model';

type EntityResponseType = HttpResponse<IGesAlerta>;
type EntityArrayResponseType = HttpResponse<IGesAlerta[]>;

@Injectable({ providedIn: 'root' })
export class GesAlertaService {
  public resourceUrl = SERVER_API_URL + 'api/ges-alertas';

  constructor(protected http: HttpClient) {}

  create(gesAlerta: IGesAlerta): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(gesAlerta);
    return this.http
      .post<IGesAlerta>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(gesAlerta: IGesAlerta): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(gesAlerta);
    return this.http
      .put<IGesAlerta>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IGesAlerta>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IGesAlerta[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(gesAlerta: IGesAlerta): IGesAlerta {
    const copy: IGesAlerta = Object.assign({}, gesAlerta, {
      fechaAlerta: gesAlerta.fechaAlerta && gesAlerta.fechaAlerta.isValid() ? gesAlerta.fechaAlerta.toJSON() : undefined,
      fechaCreacion: gesAlerta.fechaCreacion && gesAlerta.fechaCreacion.isValid() ? gesAlerta.fechaCreacion.toJSON() : undefined,
      fechaModificacion:
        gesAlerta.fechaModificacion && gesAlerta.fechaModificacion.isValid() ? gesAlerta.fechaModificacion.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.fechaAlerta = res.body.fechaAlerta ? moment(res.body.fechaAlerta) : undefined;
      res.body.fechaCreacion = res.body.fechaCreacion ? moment(res.body.fechaCreacion) : undefined;
      res.body.fechaModificacion = res.body.fechaModificacion ? moment(res.body.fechaModificacion) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((gesAlerta: IGesAlerta) => {
        gesAlerta.fechaAlerta = gesAlerta.fechaAlerta ? moment(gesAlerta.fechaAlerta) : undefined;
        gesAlerta.fechaCreacion = gesAlerta.fechaCreacion ? moment(gesAlerta.fechaCreacion) : undefined;
        gesAlerta.fechaModificacion = gesAlerta.fechaModificacion ? moment(gesAlerta.fechaModificacion) : undefined;
      });
    }
    return res;
  }
}
