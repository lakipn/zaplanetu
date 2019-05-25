/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { ZaPlanetuTestModule } from '../../../test.module';
import { OdgovorUpdateComponent } from 'app/entities/odgovor/odgovor-update.component';
import { OdgovorService } from 'app/entities/odgovor/odgovor.service';
import { Odgovor } from 'app/shared/model/odgovor.model';

describe('Component Tests', () => {
  describe('Odgovor Management Update Component', () => {
    let comp: OdgovorUpdateComponent;
    let fixture: ComponentFixture<OdgovorUpdateComponent>;
    let service: OdgovorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ZaPlanetuTestModule],
        declarations: [OdgovorUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(OdgovorUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OdgovorUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OdgovorService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Odgovor(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Odgovor();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
