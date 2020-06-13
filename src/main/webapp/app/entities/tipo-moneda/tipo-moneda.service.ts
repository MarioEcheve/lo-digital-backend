import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITipoMoneda } from 'app/shared/model/tipo-moneda.model';

type EntityResponseType = HttpResponse<ITipoMoneda>;
type EntityArrayResponseType = HttpResponse<ITipoMoneda[]>;

@Injectable({ providedIn: 'root' })
export class TipoMonedaService {
  public resourceUrl = SERVER_API_URL + 'api/tipo-monedas';

  constructor(protected http: HttpClient) {}

  create(tipoMoneda: ITipoMoneda): Observable<EntityResponseType> {
    return this.http.post<ITipoMoneda>(this.resourceUrl, tipoMoneda, { observe: 'response' });
  }

  update(tipoMoneda: ITipoMoneda): Observable<EntityResponseType> {
    return this.http.put<ITipoMoneda>(this.resourceUrl, tipoMoneda, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITipoMoneda>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITipoMoneda[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
