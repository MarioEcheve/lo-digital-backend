import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IActividadRubro } from 'app/shared/model/actividad-rubro.model';

type EntityResponseType = HttpResponse<IActividadRubro>;
type EntityArrayResponseType = HttpResponse<IActividadRubro[]>;

@Injectable({ providedIn: 'root' })
export class ActividadRubroService {
  public resourceUrl = SERVER_API_URL + 'api/actividad-rubros';

  constructor(protected http: HttpClient) {}

  create(actividadRubro: IActividadRubro): Observable<EntityResponseType> {
    return this.http.post<IActividadRubro>(this.resourceUrl, actividadRubro, { observe: 'response' });
  }

  update(actividadRubro: IActividadRubro): Observable<EntityResponseType> {
    return this.http.put<IActividadRubro>(this.resourceUrl, actividadRubro, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IActividadRubro>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IActividadRubro[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
