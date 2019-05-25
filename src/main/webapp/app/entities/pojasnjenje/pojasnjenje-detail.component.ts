import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IPojasnjenje } from 'app/shared/model/pojasnjenje.model';

@Component({
  selector: 'jhi-pojasnjenje-detail',
  templateUrl: './pojasnjenje-detail.component.html'
})
export class PojasnjenjeDetailComponent implements OnInit {
  pojasnjenje: IPojasnjenje;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pojasnjenje }) => {
      this.pojasnjenje = pojasnjenje;
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
