/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ZaPlanetuTestModule } from '../../../test.module';
import { PitanjeDeleteDialogComponent } from 'app/entities/pitanje/pitanje-delete-dialog.component';
import { PitanjeService } from 'app/entities/pitanje/pitanje.service';

describe('Component Tests', () => {
  describe('Pitanje Management Delete Component', () => {
    let comp: PitanjeDeleteDialogComponent;
    let fixture: ComponentFixture<PitanjeDeleteDialogComponent>;
    let service: PitanjeService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ZaPlanetuTestModule],
        declarations: [PitanjeDeleteDialogComponent]
      })
        .overrideTemplate(PitanjeDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PitanjeDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PitanjeService);
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
