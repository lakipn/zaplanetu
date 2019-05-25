/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ZaPlanetuTestModule } from '../../../test.module';
import { SekcijaDetailComponent } from 'app/entities/sekcija/sekcija-detail.component';
import { Sekcija } from 'app/shared/model/sekcija.model';

describe('Component Tests', () => {
  describe('Sekcija Management Detail Component', () => {
    let comp: SekcijaDetailComponent;
    let fixture: ComponentFixture<SekcijaDetailComponent>;
    const route = ({ data: of({ sekcija: new Sekcija(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ZaPlanetuTestModule],
        declarations: [SekcijaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SekcijaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SekcijaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sekcija).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
