import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITipoUsuarioDependencia } from 'app/shared/model/tipo-usuario-dependencia.model';

type EntityResponseType = HttpResponse<ITipoUsuarioDependencia>;
type EntityArrayResponseType = HttpResponse<ITipoUsuarioDependencia[]>;

@Injectable({ providedIn: 'root' })
export class TipoUsuarioDependenciaService {
  public resourceUrl = SERVER_API_URL + 'api/tipo-usuario-dependencias';

  constructor(protected http: HttpClient) {}

  create(tipoUsuarioDependencia: ITipoUsuarioDependencia): Observable<EntityResponseType> {
    return this.http.post<ITipoUsuarioDependencia>(this.resourceUrl, tipoUsuarioDependencia, { observe: 'response' });
  }

  update(tipoUsuarioDependencia: ITipoUsuarioDependencia): Observable<EntityResponseType> {
    return this.http.put<ITipoUsuarioDependencia>(this.resourceUrl, tipoUsuarioDependencia, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITipoUsuarioDependencia>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITipoUsuarioDependencia[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
