import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ISekcija } from 'app/shared/model/sekcija.model';

@Component({
  selector: 'jhi-sekcija-detail',
  templateUrl: './sekcija-detail.component.html'
})
export class SekcijaDetailComponent implements OnInit {
  sekcija: ISekcija;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ sekcija }) => {
      this.sekcija = sekcija;
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
