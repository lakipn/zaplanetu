import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ZaPlanetuSharedModule } from 'app/shared';
import {
  PoenComponent,
  PoenDetailComponent,
  PoenUpdateComponent,
  PoenDeletePopupComponent,
  PoenDeleteDialogComponent,
  poenRoute,
  poenPopupRoute
} from './';

const ENTITY_STATES = [...poenRoute, ...poenPopupRoute];

@NgModule({
  imports: [ZaPlanetuSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [PoenComponent, PoenDetailComponent, PoenUpdateComponent, PoenDeleteDialogComponent, PoenDeletePopupComponent],
  entryComponents: [PoenComponent, PoenUpdateComponent, PoenDeleteDialogComponent, PoenDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ZaPlanetuPoenModule {}
