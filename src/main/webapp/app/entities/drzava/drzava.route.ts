import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Drzava } from 'app/shared/model/drzava.model';
import { DrzavaService } from './drzava.service';
import { DrzavaComponent } from './drzava.component';
import { DrzavaDetailComponent } from './drzava-detail.component';
import { DrzavaUpdateComponent } from './drzava-update.component';
import { DrzavaDeletePopupComponent } from './drzava-delete-dialog.component';
import { IDrzava } from 'app/shared/model/drzava.model';

@Injectable({ providedIn: 'root' })
export class DrzavaResolve implements Resolve<IDrzava> {
  constructor(private service: DrzavaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDrzava> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Drzava>) => response.ok),
        map((drzava: HttpResponse<Drzava>) => drzava.body)
      );
    }
    return of(new Drzava());
  }
}

export const drzavaRoute: Routes = [
  {
    path: '',
    component: DrzavaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Drzavas'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DrzavaDetailComponent,
    resolve: {
      drzava: DrzavaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Drzavas'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DrzavaUpdateComponent,
    resolve: {
      drzava: DrzavaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Drzavas'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DrzavaUpdateComponent,
    resolve: {
      drzava: DrzavaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Drzavas'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const drzavaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DrzavaDeletePopupComponent,
    resolve: {
      drzava: DrzavaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Drzavas'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
