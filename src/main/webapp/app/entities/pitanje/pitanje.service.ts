import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPitanje } from 'app/shared/model/pitanje.model';

type EntityResponseType = HttpResponse<IPitanje>;
type EntityArrayResponseType = HttpResponse<IPitanje[]>;

@Injectable({ providedIn: 'root' })
export class PitanjeService {
  public resourceUrl = SERVER_API_URL + 'api/pitanjes';

  constructor(protected http: HttpClient) {}

  create(pitanje: IPitanje): Observable<EntityResponseType> {
    return this.http.post<IPitanje>(this.resourceUrl, pitanje, { observe: 'response' });
  }

  update(pitanje: IPitanje): Observable<EntityResponseType> {
    return this.http.put<IPitanje>(this.resourceUrl, pitanje, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPitanje>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPitanje[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
