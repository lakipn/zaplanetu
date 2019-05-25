import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ZaPlanetuSharedModule } from 'app/shared';
import {
  PitanjeComponent,
  PitanjeDetailComponent,
  PitanjeUpdateComponent,
  PitanjeDeletePopupComponent,
  PitanjeDeleteDialogComponent,
  pitanjeRoute,
  pitanjePopupRoute
} from './';

const ENTITY_STATES = [...pitanjeRoute, ...pitanjePopupRoute];

@NgModule({
  imports: [ZaPlanetuSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PitanjeComponent,
    PitanjeDetailComponent,
    PitanjeUpdateComponent,
    PitanjeDeleteDialogComponent,
    PitanjeDeletePopupComponent
  ],
  entryComponents: [PitanjeComponent, PitanjeUpdateComponent, PitanjeDeleteDialogComponent, PitanjeDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ZaPlanetuPitanjeModule {}
