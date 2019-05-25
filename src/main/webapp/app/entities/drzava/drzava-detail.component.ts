import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IDrzava } from 'app/shared/model/drzava.model';

@Component({
  selector: 'jhi-drzava-detail',
  templateUrl: './drzava-detail.component.html'
})
export class DrzavaDetailComponent implements OnInit {
  drzava: IDrzava;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ drzava }) => {
      this.drzava = drzava;
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }
  previousState() {
    window.history.back();
  }
}
