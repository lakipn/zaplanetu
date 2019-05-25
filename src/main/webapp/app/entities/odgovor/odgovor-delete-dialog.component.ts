import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOdgovor } from 'app/shared/model/odgovor.model';
import { OdgovorService } from './odgovor.service';

@Component({
  selector: 'jhi-odgovor-delete-dialog',
  templateUrl: './odgovor-delete-dialog.component.html'
})
export class OdgovorDeleteDialogComponent {
  odgovor: IOdgovor;

  constructor(protected odgovorService: OdgovorService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.odgovorService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'odgovorListModification',
        content: 'Deleted an odgovor'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-odgovor-delete-popup',
  template: ''
})
export class OdgovorDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ odgovor }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(OdgovorDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.odgovor = odgovor;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/odgovor', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/odgovor', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
