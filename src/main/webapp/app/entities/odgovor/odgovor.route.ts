import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Odgovor } from 'app/shared/model/odgovor.model';
import { OdgovorService } from './odgovor.service';
import { OdgovorComponent } from './odgovor.component';
import { OdgovorDetailComponent } from './odgovor-detail.component';
import { OdgovorUpdateComponent } from './odgovor-update.component';
import { OdgovorDeletePopupComponent } from './odgovor-delete-dialog.component';
import { IOdgovor } from 'app/shared/model/odgovor.model';

@Injectable({ providedIn: 'root' })
export class OdgovorResolve implements Resolve<IOdgovor> {
  constructor(private service: OdgovorService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IOdgovor> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Odgovor>) => response.ok),
        map((odgovor: HttpResponse<Odgovor>) => odgovor.body)
      );
    }
    return of(new Odgovor());
  }
}

export const odgovorRoute: Routes = [
  {
    path: '',
    component: OdgovorComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Odgovors'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: OdgovorDetailComponent,
    resolve: {
      odgovor: OdgovorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Odgovors'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: OdgovorUpdateComponent,
    resolve: {
      odgovor: OdgovorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Odgovors'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: OdgovorUpdateComponent,
    resolve: {
      odgovor: OdgovorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Odgovors'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const odgovorPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: OdgovorDeletePopupComponent,
    resolve: {
      odgovor: OdgovorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Odgovors'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
