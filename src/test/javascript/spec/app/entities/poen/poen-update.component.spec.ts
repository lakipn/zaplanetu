/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { ZaPlanetuTestModule } from '../../../test.module';
import { PoenUpdateComponent } from 'app/entities/poen/poen-update.component';
import { PoenService } from 'app/entities/poen/poen.service';
import { Poen } from 'app/shared/model/poen.model';

describe('Component Tests', () => {
  describe('Poen Management Update Component', () => {
    let comp: PoenUpdateComponent;
    let fixture: ComponentFixture<PoenUpdateComponent>;
    let service: PoenService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ZaPlanetuTestModule],
        declarations: [PoenUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PoenUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PoenUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PoenService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Poen(123);
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
        const entity = new Poen();
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
