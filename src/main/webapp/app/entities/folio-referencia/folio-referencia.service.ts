import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFolioReferencia } from 'app/shared/model/folio-referencia.model';

type EntityResponseType = HttpResponse<IFolioReferencia>;
type EntityArrayResponseType = HttpResponse<IFolioReferencia[]>;

@Injectable({ providedIn: 'root' })
export class FolioReferenciaService {
  public resourceUrl = SERVER_API_URL + 'api/folio-referencias';

  constructor(protected http: HttpClient) {}

  create(folioReferencia: IFolioReferencia): Observable<EntityResponseType> {
    return this.http.post<IFolioReferencia>(this.resourceUrl, folioReferencia, { observe: 'response' });
  }

  update(folioReferencia: IFolioReferencia): Observable<EntityResponseType> {
    return this.http.put<IFolioReferencia>(this.resourceUrl, folioReferencia, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFolioReferencia>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFolioReferencia[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
