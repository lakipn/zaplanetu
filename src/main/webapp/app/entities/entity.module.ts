import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'sekcija',
        loadChildren: './sekcija/sekcija.module#ZaPlanetuSekcijaModule'
      },
      {
        path: 'pitanje',
        loadChildren: './pitanje/pitanje.module#ZaPlanetuPitanjeModule'
      },
      {
        path: 'odgovor',
        loadChildren: './odgovor/odgovor.module#ZaPlanetuOdgovorModule'
      },
      {
        path: 'pojasnjenje',
        loadChildren: './pojasnjenje/pojasnjenje.module#ZaPlanetuPojasnjenjeModule'
      },
      {
        path: 'drzava',
        loadChildren: './drzava/drzava.module#ZaPlanetuDrzavaModule'
      },
      {
        path: 'poen',
        loadChildren: './poen/poen.module#ZaPlanetuPoenModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ZaPlanetuEntityModule {}
