import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ZaPlanetuSharedModule } from 'app/shared';
import {
  SekcijaComponent,
  SekcijaDetailComponent,
  SekcijaUpdateComponent,
  SekcijaDeletePopupComponent,
  SekcijaDeleteDialogComponent,
  sekcijaRoute,
  sekcijaPopupRoute
} from './';

const ENTITY_STATES = [...sekcijaRoute, ...sekcijaPopupRoute];

@NgModule({
  imports: [ZaPlanetuSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SekcijaComponent,
    SekcijaDetailComponent,
    SekcijaUpdateComponent,
    SekcijaDeleteDialogComponent,
    SekcijaDeletePopupComponent
  ],
  entryComponents: [SekcijaComponent, SekcijaUpdateComponent, SekcijaDeleteDialogComponent, SekcijaDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ZaPlanetuSekcijaModule {}
