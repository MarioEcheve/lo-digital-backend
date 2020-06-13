import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEstadoRespuesta } from 'app/shared/model/estado-respuesta.model';

type EntityResponseType = HttpResponse<IEstadoRespuesta>;
type EntityArrayResponseType = HttpResponse<IEstadoRespuesta[]>;

@Injectable({ providedIn: 'root' })
export class EstadoRespuestaService {
  public resourceUrl = SERVER_API_URL + 'api/estado-respuestas';

  constructor(protected http: HttpClient) {}

  create(estadoRespuesta: IEstadoRespuesta): Observable<EntityResponseType> {
    return this.http.post<IEstadoRespuesta>(this.resourceUrl, estadoRespuesta, { observe: 'response' });
  }

  update(estadoRespuesta: IEstadoRespuesta): Observable<EntityResponseType> {
    return this.http.put<IEstadoRespuesta>(this.resourceUrl, estadoRespuesta, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEstadoRespuesta>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEstadoRespuesta[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
