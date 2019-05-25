import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ZaPlanetuSharedModule } from 'app/shared';
import {
  PojasnjenjeComponent,
  PojasnjenjeDetailComponent,
  PojasnjenjeUpdateComponent,
  PojasnjenjeDeletePopupComponent,
  PojasnjenjeDeleteDialogComponent,
  pojasnjenjeRoute,
  pojasnjenjePopupRoute
} from './';

const ENTITY_STATES = [...pojasnjenjeRoute, ...pojasnjenjePopupRoute];

@NgModule({
  imports: [ZaPlanetuSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PojasnjenjeComponent,
    PojasnjenjeDetailComponent,
    PojasnjenjeUpdateComponent,
    PojasnjenjeDeleteDialogComponent,
    PojasnjenjeDeletePopupComponent
  ],
  entryComponents: [PojasnjenjeComponent, PojasnjenjeUpdateComponent, PojasnjenjeDeleteDialogComponent, PojasnjenjeDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ZaPlanetuPojasnjenjeModule {}
