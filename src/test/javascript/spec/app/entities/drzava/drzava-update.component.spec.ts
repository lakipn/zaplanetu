/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { ZaPlanetuTestModule } from '../../../test.module';
import { DrzavaUpdateComponent } from 'app/entities/drzava/drzava-update.component';
import { DrzavaService } from 'app/entities/drzava/drzava.service';
import { Drzava } from 'app/shared/model/drzava.model';

describe('Component Tests', () => {
  describe('Drzava Management Update Component', () => {
    let comp: DrzavaUpdateComponent;
    let fixture: ComponentFixture<DrzavaUpdateComponent>;
    let service: DrzavaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ZaPlanetuTestModule],
        declarations: [DrzavaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DrzavaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DrzavaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DrzavaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Drzava(123);
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
        const entity = new Drzava();
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
