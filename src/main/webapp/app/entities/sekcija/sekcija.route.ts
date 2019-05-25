import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Sekcija } from 'app/shared/model/sekcija.model';
import { SekcijaService } from './sekcija.service';
import { SekcijaComponent } from './sekcija.component';
import { SekcijaDetailComponent } from './sekcija-detail.component';
import { SekcijaUpdateComponent } from './sekcija-update.component';
import { SekcijaDeletePopupComponent } from './sekcija-delete-dialog.component';
import { ISekcija } from 'app/shared/model/sekcija.model';

@Injectable({ providedIn: 'root' })
export class SekcijaResolve implements Resolve<ISekcija> {
  constructor(private service: SekcijaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISekcija> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Sekcija>) => response.ok),
        map((sekcija: HttpResponse<Sekcija>) => sekcija.body)
      );
    }
    return of(new Sekcija());
  }
}

export const sekcijaRoute: Routes = [
  {
    path: '',
    component: SekcijaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Sekcijas'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SekcijaDetailComponent,
    resolve: {
      sekcija: SekcijaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Sekcijas'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SekcijaUpdateComponent,
    resolve: {
      sekcija: SekcijaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Sekcijas'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SekcijaUpdateComponent,
    resolve: {
      sekcija: SekcijaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Sekcijas'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const sekcijaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SekcijaDeletePopupComponent,
    resolve: {
      sekcija: SekcijaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Sekcijas'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
