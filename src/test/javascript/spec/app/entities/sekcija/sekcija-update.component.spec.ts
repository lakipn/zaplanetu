/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { ZaPlanetuTestModule } from '../../../test.module';
import { SekcijaUpdateComponent } from 'app/entities/sekcija/sekcija-update.component';
import { SekcijaService } from 'app/entities/sekcija/sekcija.service';
import { Sekcija } from 'app/shared/model/sekcija.model';

describe('Component Tests', () => {
  describe('Sekcija Management Update Component', () => {
    let comp: SekcijaUpdateComponent;
    let fixture: ComponentFixture<SekcijaUpdateComponent>;
    let service: SekcijaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ZaPlanetuTestModule],
        declarations: [SekcijaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SekcijaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SekcijaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SekcijaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Sekcija(123);
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
        const entity = new Sekcija();
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
