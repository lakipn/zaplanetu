import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISekcija } from 'app/shared/model/sekcija.model';

type EntityResponseType = HttpResponse<ISekcija>;
type EntityArrayResponseType = HttpResponse<ISekcija[]>;

@Injectable({ providedIn: 'root' })
export class SekcijaService {
  public resourceUrl = SERVER_API_URL + 'api/sekcijas';

  constructor(protected http: HttpClient) {}

  create(sekcija: ISekcija): Observable<EntityResponseType> {
    return this.http.post<ISekcija>(this.resourceUrl, sekcija, { observe: 'response' });
  }

  update(sekcija: ISekcija): Observable<EntityResponseType> {
    return this.http.put<ISekcija>(this.resourceUrl, sekcija, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISekcija>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISekcija[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
