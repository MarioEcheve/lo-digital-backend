import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IComuna } from 'app/shared/model/comuna.model';

type EntityResponseType = HttpResponse<IComuna>;
type EntityArrayResponseType = HttpResponse<IComuna[]>;

@Injectable({ providedIn: 'root' })
export class ComunaService {
  public resourceUrl = SERVER_API_URL + 'api/comunas';

  constructor(protected http: HttpClient) {}

  create(comuna: IComuna): Observable<EntityResponseType> {
    return this.http.post<IComuna>(this.resourceUrl, comuna, { observe: 'response' });
  }

  update(comuna: IComuna): Observable<EntityResponseType> {
    return this.http.put<IComuna>(this.resourceUrl, comuna, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IComuna>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IComuna[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
