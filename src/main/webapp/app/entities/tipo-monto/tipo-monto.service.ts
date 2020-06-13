import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITipoMonto } from 'app/shared/model/tipo-monto.model';

type EntityResponseType = HttpResponse<ITipoMonto>;
type EntityArrayResponseType = HttpResponse<ITipoMonto[]>;

@Injectable({ providedIn: 'root' })
export class TipoMontoService {
  public resourceUrl = SERVER_API_URL + 'api/tipo-montos';

  constructor(protected http: HttpClient) {}

  create(tipoMonto: ITipoMonto): Observable<EntityResponseType> {
    return this.http.post<ITipoMonto>(this.resourceUrl, tipoMonto, { observe: 'response' });
  }

  update(tipoMonto: ITipoMonto): Observable<EntityResponseType> {
    return this.http.put<ITipoMonto>(this.resourceUrl, tipoMonto, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITipoMonto>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITipoMonto[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
