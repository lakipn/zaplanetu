import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Pitanje } from 'app/shared/model/pitanje.model';
import { PitanjeService } from './pitanje.service';
import { PitanjeComponent } from './pitanje.component';
import { PitanjeDetailComponent } from './pitanje-detail.component';
import { PitanjeUpdateComponent } from './pitanje-update.component';
import { PitanjeDeletePopupComponent } from './pitanje-delete-dialog.component';
import { IPitanje } from 'app/shared/model/pitanje.model';

@Injectable({ providedIn: 'root' })
export class PitanjeResolve implements Resolve<IPitanje> {
  constructor(private service: PitanjeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPitanje> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Pitanje>) => response.ok),
        map((pitanje: HttpResponse<Pitanje>) => pitanje.body)
      );
    }
    return of(new Pitanje());
  }
}

export const pitanjeRoute: Routes = [
  {
    path: '',
    component: PitanjeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Pitanjes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PitanjeDetailComponent,
    resolve: {
      pitanje: PitanjeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Pitanjes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PitanjeUpdateComponent,
    resolve: {
      pitanje: PitanjeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Pitanjes'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PitanjeUpdateComponent,
    resolve: {
      pitanje: PitanjeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Pitanjes'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const pitanjePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PitanjeDeletePopupComponent,
    resolve: {
      pitanje: PitanjeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Pitanjes'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
