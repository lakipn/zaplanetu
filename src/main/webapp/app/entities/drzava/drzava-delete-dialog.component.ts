import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDrzava } from 'app/shared/model/drzava.model';
import { DrzavaService } from './drzava.service';

@Component({
  selector: 'jhi-drzava-delete-dialog',
  templateUrl: './drzava-delete-dialog.component.html'
})
export class DrzavaDeleteDialogComponent {
  drzava: IDrzava;

  constructor(protected drzavaService: DrzavaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.drzavaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'drzavaListModification',
        content: 'Deleted an drzava'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-drzava-delete-popup',
  template: ''
})
export class DrzavaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ drzava }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DrzavaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.drzava = drzava;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/drzava', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/drzava', { outlets: { popup: null } }]);
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
