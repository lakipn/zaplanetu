import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPojasnjenje } from 'app/shared/model/pojasnjenje.model';
import { PojasnjenjeService } from './pojasnjenje.service';

@Component({
  selector: 'jhi-pojasnjenje-delete-dialog',
  templateUrl: './pojasnjenje-delete-dialog.component.html'
})
export class PojasnjenjeDeleteDialogComponent {
  pojasnjenje: IPojasnjenje;

  constructor(
    protected pojasnjenjeService: PojasnjenjeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.pojasnjenjeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'pojasnjenjeListModification',
        content: 'Deleted an pojasnjenje'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-pojasnjenje-delete-popup',
  template: ''
})
export class PojasnjenjeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pojasnjenje }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PojasnjenjeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.pojasnjenje = pojasnjenje;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/pojasnjenje', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/pojasnjenje', { outlets: { popup: null } }]);
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
