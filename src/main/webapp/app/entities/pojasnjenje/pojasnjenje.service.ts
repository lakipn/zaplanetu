import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPojasnjenje } from 'app/shared/model/pojasnjenje.model';

type EntityResponseType = HttpResponse<IPojasnjenje>;
type EntityArrayResponseType = HttpResponse<IPojasnjenje[]>;

@Injectable({ providedIn: 'root' })
export class PojasnjenjeService {
  public resourceUrl = SERVER_API_URL + 'api/pojasnjenjes';

  constructor(protected http: HttpClient) {}

  create(pojasnjenje: IPojasnjenje): Observable<EntityResponseType> {
    return this.http.post<IPojasnjenje>(this.resourceUrl, pojasnjenje, { observe: 'response' });
  }

  update(pojasnjenje: IPojasnjenje): Observable<EntityResponseType> {
    return this.http.put<IPojasnjenje>(this.resourceUrl, pojasnjenje, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPojasnjenje>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPojasnjenje[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
