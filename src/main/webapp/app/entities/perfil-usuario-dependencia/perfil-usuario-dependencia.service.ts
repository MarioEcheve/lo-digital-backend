import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPerfilUsuarioDependencia } from 'app/shared/model/perfil-usuario-dependencia.model';

type EntityResponseType = HttpResponse<IPerfilUsuarioDependencia>;
type EntityArrayResponseType = HttpResponse<IPerfilUsuarioDependencia[]>;

@Injectable({ providedIn: 'root' })
export class PerfilUsuarioDependenciaService {
  public resourceUrl = SERVER_API_URL + 'api/perfil-usuario-dependencias';

  constructor(protected http: HttpClient) {}

  create(perfilUsuarioDependencia: IPerfilUsuarioDependencia): Observable<EntityResponseType> {
    return this.http.post<IPerfilUsuarioDependencia>(this.resourceUrl, perfilUsuarioDependencia, { observe: 'response' });
  }

  update(perfilUsuarioDependencia: IPerfilUsuarioDependencia): Observable<EntityResponseType> {
    return this.http.put<IPerfilUsuarioDependencia>(this.resourceUrl, perfilUsuarioDependencia, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPerfilUsuarioDependencia>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPerfilUsuarioDependencia[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
