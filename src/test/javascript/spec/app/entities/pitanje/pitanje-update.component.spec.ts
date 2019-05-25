/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { ZaPlanetuTestModule } from '../../../test.module';
import { PitanjeUpdateComponent } from 'app/entities/pitanje/pitanje-update.component';
import { PitanjeService } from 'app/entities/pitanje/pitanje.service';
import { Pitanje } from 'app/shared/model/pitanje.model';

describe('Component Tests', () => {
  describe('Pitanje Management Update Component', () => {
    let comp: PitanjeUpdateComponent;
    let fixture: ComponentFixture<PitanjeUpdateComponent>;
    let service: PitanjeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ZaPlanetuTestModule],
        declarations: [PitanjeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PitanjeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PitanjeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PitanjeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Pitanje(123);
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
        const entity = new Pitanje();
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
