import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ZaPlanetuSharedModule } from 'app/shared';
import {
  DrzavaComponent,
  DrzavaDetailComponent,
  DrzavaUpdateComponent,
  DrzavaDeletePopupComponent,
  DrzavaDeleteDialogComponent,
  drzavaRoute,
  drzavaPopupRoute
} from './';

const ENTITY_STATES = [...drzavaRoute, ...drzavaPopupRoute];

@NgModule({
  imports: [ZaPlanetuSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [DrzavaComponent, DrzavaDetailComponent, DrzavaUpdateComponent, DrzavaDeleteDialogComponent, DrzavaDeletePopupComponent],
  entryComponents: [DrzavaComponent, DrzavaUpdateComponent, DrzavaDeleteDialogComponent, DrzavaDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ZaPlanetuDrzavaModule {}
