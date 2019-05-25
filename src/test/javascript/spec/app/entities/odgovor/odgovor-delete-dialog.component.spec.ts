/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ZaPlanetuTestModule } from '../../../test.module';
import { OdgovorDeleteDialogComponent } from 'app/entities/odgovor/odgovor-delete-dialog.component';
import { OdgovorService } from 'app/entities/odgovor/odgovor.service';

describe('Component Tests', () => {
  describe('Odgovor Management Delete Component', () => {
    let comp: OdgovorDeleteDialogComponent;
    let fixture: ComponentFixture<OdgovorDeleteDialogComponent>;
    let service: OdgovorService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ZaPlanetuTestModule],
        declarations: [OdgovorDeleteDialogComponent]
      })
        .overrideTemplate(OdgovorDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OdgovorDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OdgovorService);
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
