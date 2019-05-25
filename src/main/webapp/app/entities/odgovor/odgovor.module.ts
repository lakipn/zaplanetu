import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ZaPlanetuSharedModule } from 'app/shared';
import {
  OdgovorComponent,
  OdgovorDetailComponent,
  OdgovorUpdateComponent,
  OdgovorDeletePopupComponent,
  OdgovorDeleteDialogComponent,
  odgovorRoute,
  odgovorPopupRoute
} from './';

const ENTITY_STATES = [...odgovorRoute, ...odgovorPopupRoute];

@NgModule({
  imports: [ZaPlanetuSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    OdgovorComponent,
    OdgovorDetailComponent,
    OdgovorUpdateComponent,
    OdgovorDeleteDialogComponent,
    OdgovorDeletePopupComponent
  ],
  entryComponents: [OdgovorComponent, OdgovorUpdateComponent, OdgovorDeleteDialogComponent, OdgovorDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ZaPlanetuOdgovorModule {}
