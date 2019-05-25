/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ZaPlanetuTestModule } from '../../../test.module';
import { OdgovorDetailComponent } from 'app/entities/odgovor/odgovor-detail.component';
import { Odgovor } from 'app/shared/model/odgovor.model';

describe('Component Tests', () => {
  describe('Odgovor Management Detail Component', () => {
    let comp: OdgovorDetailComponent;
    let fixture: ComponentFixture<OdgovorDetailComponent>;
    const route = ({ data: of({ odgovor: new Odgovor(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ZaPlanetuTestModule],
        declarations: [OdgovorDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(OdgovorDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OdgovorDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.odgovor).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
