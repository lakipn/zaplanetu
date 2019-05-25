/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ZaPlanetuTestModule } from '../../../test.module';
import { PojasnjenjeDeleteDialogComponent } from 'app/entities/pojasnjenje/pojasnjenje-delete-dialog.component';
import { PojasnjenjeService } from 'app/entities/pojasnjenje/pojasnjenje.service';

describe('Component Tests', () => {
  describe('Pojasnjenje Management Delete Component', () => {
    let comp: PojasnjenjeDeleteDialogComponent;
    let fixture: ComponentFixture<PojasnjenjeDeleteDialogComponent>;
    let service: PojasnjenjeService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ZaPlanetuTestModule],
        declarations: [PojasnjenjeDeleteDialogComponent]
      })
        .overrideTemplate(PojasnjenjeDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PojasnjenjeDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PojasnjenjeService);
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
