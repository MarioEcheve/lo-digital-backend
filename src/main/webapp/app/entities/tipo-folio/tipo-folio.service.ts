import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITipoFolio } from 'app/shared/model/tipo-folio.model';

type EntityResponseType = HttpResponse<ITipoFolio>;
type EntityArrayResponseType = HttpResponse<ITipoFolio[]>;

@Injectable({ providedIn: 'root' })
export class TipoFolioService {
  public resourceUrl = SERVER_API_URL + 'api/tipo-folios';

  constructor(protected http: HttpClient) {}

  create(tipoFolio: ITipoFolio): Observable<EntityResponseType> {
    return this.http.post<ITipoFolio>(this.resourceUrl, tipoFolio, { observe: 'response' });
  }

  update(tipoFolio: ITipoFolio): Observable<EntityResponseType> {
    return this.http.put<ITipoFolio>(this.resourceUrl, tipoFolio, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITipoFolio>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITipoFolio[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
