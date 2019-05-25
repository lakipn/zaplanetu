import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPitanje } from 'app/shared/model/pitanje.model';
import { PitanjeService } from './pitanje.service';

@Component({
  selector: 'jhi-pitanje-delete-dialog',
  templateUrl: './pitanje-delete-dialog.component.html'
})
export class PitanjeDeleteDialogComponent {
  pitanje: IPitanje;

  constructor(protected pitanjeService: PitanjeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.pitanjeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'pitanjeListModification',
        content: 'Deleted an pitanje'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-pitanje-delete-popup',
  template: ''
})
export class PitanjeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pitanje }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PitanjeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.pitanje = pitanje;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/pitanje', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/pitanje', { outlets: { popup: null } }]);
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
