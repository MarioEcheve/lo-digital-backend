import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEstadoServicio } from 'app/shared/model/estado-servicio.model';

type EntityResponseType = HttpResponse<IEstadoServicio>;
type EntityArrayResponseType = HttpResponse<IEstadoServicio[]>;

@Injectable({ providedIn: 'root' })
export class EstadoServicioService {
  public resourceUrl = SERVER_API_URL + 'api/estado-servicios';

  constructor(protected http: HttpClient) {}

  create(estadoServicio: IEstadoServicio): Observable<EntityResponseType> {
    return this.http.post<IEstadoServicio>(this.resourceUrl, estadoServicio, { observe: 'response' });
  }

  update(estadoServicio: IEstadoServicio): Observable<EntityResponseType> {
    return this.http.put<IEstadoServicio>(this.resourceUrl, estadoServicio, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEstadoServicio>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEstadoServicio[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
