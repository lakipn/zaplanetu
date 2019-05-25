import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IOdgovor } from 'app/shared/model/odgovor.model';

type EntityResponseType = HttpResponse<IOdgovor>;
type EntityArrayResponseType = HttpResponse<IOdgovor[]>;

@Injectable({ providedIn: 'root' })
export class OdgovorService {
  public resourceUrl = SERVER_API_URL + 'api/odgovors';

  constructor(protected http: HttpClient) {}

  create(odgovor: IOdgovor): Observable<EntityResponseType> {
    return this.http.post<IOdgovor>(this.resourceUrl, odgovor, { observe: 'response' });
  }

  update(odgovor: IOdgovor): Observable<EntityResponseType> {
    return this.http.put<IOdgovor>(this.resourceUrl, odgovor, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOdgovor>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOdgovor[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
