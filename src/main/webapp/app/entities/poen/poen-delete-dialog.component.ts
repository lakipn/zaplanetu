import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPoen } from 'app/shared/model/poen.model';
import { PoenService } from './poen.service';

@Component({
  selector: 'jhi-poen-delete-dialog',
  templateUrl: './poen-delete-dialog.component.html'
})
export class PoenDeleteDialogComponent {
  poen: IPoen;

  constructor(protected poenService: PoenService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.poenService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'poenListModification',
        content: 'Deleted an poen'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-poen-delete-popup',
  template: ''
})
export class PoenDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ poen }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PoenDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.poen = poen;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/poen', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/poen', { outlets: { popup: null } }]);
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
