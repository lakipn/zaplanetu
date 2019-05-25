/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ZaPlanetuTestModule } from '../../../test.module';
import { DrzavaDeleteDialogComponent } from 'app/entities/drzava/drzava-delete-dialog.component';
import { DrzavaService } from 'app/entities/drzava/drzava.service';

describe('Component Tests', () => {
  describe('Drzava Management Delete Component', () => {
    let comp: DrzavaDeleteDialogComponent;
    let fixture: ComponentFixture<DrzavaDeleteDialogComponent>;
    let service: DrzavaService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ZaPlanetuTestModule],
        declarations: [DrzavaDeleteDialogComponent]
      })
        .overrideTemplate(DrzavaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DrzavaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DrzavaService);
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
