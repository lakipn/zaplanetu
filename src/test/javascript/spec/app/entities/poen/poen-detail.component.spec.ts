/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ZaPlanetuTestModule } from '../../../test.module';
import { PoenDetailComponent } from 'app/entities/poen/poen-detail.component';
import { Poen } from 'app/shared/model/poen.model';

describe('Component Tests', () => {
  describe('Poen Management Detail Component', () => {
    let comp: PoenDetailComponent;
    let fixture: ComponentFixture<PoenDetailComponent>;
    const route = ({ data: of({ poen: new Poen(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ZaPlanetuTestModule],
        declarations: [PoenDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PoenDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PoenDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.poen).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
