import { NgModule } from '@angular/core';

import { ZaPlanetuSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
  imports: [ZaPlanetuSharedLibsModule],
  declarations: [JhiAlertComponent, JhiAlertErrorComponent],
  exports: [ZaPlanetuSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class ZaPlanetuSharedCommonModule {}
