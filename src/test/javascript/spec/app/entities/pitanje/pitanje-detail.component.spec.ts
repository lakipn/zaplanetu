/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ZaPlanetuTestModule } from '../../../test.module';
import { PitanjeDetailComponent } from 'app/entities/pitanje/pitanje-detail.component';
import { Pitanje } from 'app/shared/model/pitanje.model';

describe('Component Tests', () => {
  describe('Pitanje Management Detail Component', () => {
    let comp: PitanjeDetailComponent;
    let fixture: ComponentFixture<PitanjeDetailComponent>;
    const route = ({ data: of({ pitanje: new Pitanje(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ZaPlanetuTestModule],
        declarations: [PitanjeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PitanjeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PitanjeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pitanje).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
