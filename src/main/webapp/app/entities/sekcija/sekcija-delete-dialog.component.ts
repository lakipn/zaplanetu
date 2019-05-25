import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISekcija } from 'app/shared/model/sekcija.model';
import { SekcijaService } from './sekcija.service';

@Component({
  selector: 'jhi-sekcija-delete-dialog',
  templateUrl: './sekcija-delete-dialog.component.html'
})
export class SekcijaDeleteDialogComponent {
  sekcija: ISekcija;

  constructor(protected sekcijaService: SekcijaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.sekcijaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'sekcijaListModification',
        content: 'Deleted an sekcija'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-sekcija-delete-popup',
  template: ''
})
export class SekcijaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ sekcija }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SekcijaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.sekcija = sekcija;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/sekcija', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/sekcija', { outlets: { popup: null } }]);
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
