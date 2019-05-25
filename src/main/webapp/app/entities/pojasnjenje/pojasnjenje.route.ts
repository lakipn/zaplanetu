import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Pojasnjenje } from 'app/shared/model/pojasnjenje.model';
import { PojasnjenjeService } from './pojasnjenje.service';
import { PojasnjenjeComponent } from './pojasnjenje.component';
import { PojasnjenjeDetailComponent } from './pojasnjenje-detail.component';
import { PojasnjenjeUpdateComponent } from './pojasnjenje-update.component';
import { PojasnjenjeDeletePopupComponent } from './pojasnjenje-delete-dialog.component';
import { IPojasnjenje } from 'app/shared/model/pojasnjenje.model';

@Injectable({ providedIn: 'root' })
export class PojasnjenjeResolve implements Resolve<IPojasnjenje> {
  constructor(private service: PojasnjenjeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPojasnjenje> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Pojasnjenje>) => response.ok),
        map((pojasnjenje: HttpResponse<Pojasnjenje>) => pojasnjenje.body)
      );
    }
    return of(new Pojasnjenje());
  }
}

export const pojasnjenjeRoute: Routes = [
  {
    path: '',
    component: PojasnjenjeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Pojasnjenjes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PojasnjenjeDetailComponent,
    resolve: {
      pojasnjenje: PojasnjenjeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Pojasnjenjes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PojasnjenjeUpdateComponent,
    resolve: {
      pojasnjenje: PojasnjenjeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Pojasnjenjes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PojasnjenjeUpdateComponent,
    resolve: {
      pojasnjenje: PojasnjenjeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Pojasnjenjes'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const pojasnjenjePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PojasnjenjeDeletePopupComponent,
    resolve: {
      pojasnjenje: PojasnjenjeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Pojasnjenjes'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
