import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ZaPlanetuSharedLibsModule, ZaPlanetuSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [ZaPlanetuSharedLibsModule, ZaPlanetuSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [ZaPlanetuSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ZaPlanetuSharedModule {
  static forRoot() {
    return {
      ngModule: ZaPlanetuSharedModule
    };
  }
}
