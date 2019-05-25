import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Poen } from 'app/shared/model/poen.model';
import { PoenService } from './poen.service';
import { PoenComponent } from './poen.component';
import { PoenDetailComponent } from './poen-detail.component';
import { PoenUpdateComponent } from './poen-update.component';
import { PoenDeletePopupComponent } from './poen-delete-dialog.component';
import { IPoen } from 'app/shared/model/poen.model';

@Injectable({ providedIn: 'root' })
export class PoenResolve implements Resolve<IPoen> {
  constructor(private service: PoenService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPoen> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Poen>) => response.ok),
        map((poen: HttpResponse<Poen>) => poen.body)
      );
    }
    return of(new Poen());
  }
}

export const poenRoute: Routes = [
  {
    path: '',
    component: PoenComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Poens'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PoenDetailComponent,
    resolve: {
      poen: PoenResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Poens'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PoenUpdateComponent,
    resolve: {
      poen: PoenResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Poens'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PoenUpdateComponent,
    resolve: {
      poen: PoenResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Poens'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const poenPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PoenDeletePopupComponent,
    resolve: {
      poen: PoenResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Poens'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
