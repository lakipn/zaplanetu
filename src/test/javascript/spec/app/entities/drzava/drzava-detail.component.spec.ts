/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ZaPlanetuTestModule } from '../../../test.module';
import { DrzavaDetailComponent } from 'app/entities/drzava/drzava-detail.component';
import { Drzava } from 'app/shared/model/drzava.model';

describe('Component Tests', () => {
  describe('Drzava Management Detail Component', () => {
    let comp: DrzavaDetailComponent;
    let fixture: ComponentFixture<DrzavaDetailComponent>;
    const route = ({ data: of({ drzava: new Drzava(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ZaPlanetuTestModule],
        declarations: [DrzavaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DrzavaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DrzavaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.drzava).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
