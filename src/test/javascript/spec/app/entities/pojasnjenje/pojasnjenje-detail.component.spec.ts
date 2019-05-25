/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ZaPlanetuTestModule } from '../../../test.module';
import { PojasnjenjeDetailComponent } from 'app/entities/pojasnjenje/pojasnjenje-detail.component';
import { Pojasnjenje } from 'app/shared/model/pojasnjenje.model';

describe('Component Tests', () => {
  describe('Pojasnjenje Management Detail Component', () => {
    let comp: PojasnjenjeDetailComponent;
    let fixture: ComponentFixture<PojasnjenjeDetailComponent>;
    const route = ({ data: of({ pojasnjenje: new Pojasnjenje(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ZaPlanetuTestModule],
        declarations: [PojasnjenjeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PojasnjenjeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PojasnjenjeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pojasnjenje).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
