/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ZaPlanetuTestModule } from '../../../test.module';
import { SekcijaDeleteDialogComponent } from 'app/entities/sekcija/sekcija-delete-dialog.component';
import { SekcijaService } from 'app/entities/sekcija/sekcija.service';

describe('Component Tests', () => {
  describe('Sekcija Management Delete Component', () => {
    let comp: SekcijaDeleteDialogComponent;
    let fixture: ComponentFixture<SekcijaDeleteDialogComponent>;
    let service: SekcijaService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ZaPlanetuTestModule],
        declarations: [SekcijaDeleteDialogComponent]
      })
        .overrideTemplate(SekcijaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SekcijaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SekcijaService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
