import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITipoFirma } from 'app/shared/model/tipo-firma.model';

type EntityResponseType = HttpResponse<ITipoFirma>;
type EntityArrayResponseType = HttpResponse<ITipoFirma[]>;

@Injectable({ providedIn: 'root' })
export class TipoFirmaService {
  public resourceUrl = SERVER_API_URL + 'api/tipo-firmas';

  constructor(protected http: HttpClient) {}

  create(tipoFirma: ITipoFirma): Observable<EntityResponseType> {
    return this.http.post<ITipoFirma>(this.resourceUrl, tipoFirma, { observe: 'response' });
  }

  update(tipoFirma: ITipoFirma): Observable<EntityResponseType> {
    return this.http.put<ITipoFirma>(this.resourceUrl, tipoFirma, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITipoFirma>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITipoFirma[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
