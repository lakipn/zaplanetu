import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDrzava } from 'app/shared/model/drzava.model';

type EntityResponseType = HttpResponse<IDrzava>;
type EntityArrayResponseType = HttpResponse<IDrzava[]>;

@Injectable({ providedIn: 'root' })
export class DrzavaService {
  public resourceUrl = SERVER_API_URL + 'api/drzavas';

  constructor(protected http: HttpClient) {}

  create(drzava: IDrzava): Observable<EntityResponseType> {
    return this.http.post<IDrzava>(this.resourceUrl, drzava, { observe: 'response' });
  }

  update(drzava: IDrzava): Observable<EntityResponseType> {
    return this.http.put<IDrzava>(this.resourceUrl, drzava, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDrzava>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDrzava[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
