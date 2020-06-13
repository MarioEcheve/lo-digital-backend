import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITipoEntidad } from 'app/shared/model/tipo-entidad.model';

type EntityResponseType = HttpResponse<ITipoEntidad>;
type EntityArrayResponseType = HttpResponse<ITipoEntidad[]>;

@Injectable({ providedIn: 'root' })
export class TipoEntidadService {
  public resourceUrl = SERVER_API_URL + 'api/tipo-entidads';

  constructor(protected http: HttpClient) {}

  create(tipoEntidad: ITipoEntidad): Observable<EntityResponseType> {
    return this.http.post<ITipoEntidad>(this.resourceUrl, tipoEntidad, { observe: 'response' });
  }

  update(tipoEntidad: ITipoEntidad): Observable<EntityResponseType> {
    return this.http.put<ITipoEntidad>(this.resourceUrl, tipoEntidad, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITipoEntidad>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITipoEntidad[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
