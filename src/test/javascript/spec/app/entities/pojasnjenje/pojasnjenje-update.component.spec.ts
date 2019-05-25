/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { ZaPlanetuTestModule } from '../../../test.module';
import { PojasnjenjeUpdateComponent } from 'app/entities/pojasnjenje/pojasnjenje-update.component';
import { PojasnjenjeService } from 'app/entities/pojasnjenje/pojasnjenje.service';
import { Pojasnjenje } from 'app/shared/model/pojasnjenje.model';

describe('Component Tests', () => {
  describe('Pojasnjenje Management Update Component', () => {
    let comp: PojasnjenjeUpdateComponent;
    let fixture: ComponentFixture<PojasnjenjeUpdateComponent>;
    let service: PojasnjenjeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ZaPlanetuTestModule],
        declarations: [PojasnjenjeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PojasnjenjeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PojasnjenjeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PojasnjenjeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Pojasnjenje(123);
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
        const entity = new Pojasnjenje();
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
