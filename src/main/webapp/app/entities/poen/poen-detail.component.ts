import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPoen } from 'app/shared/model/poen.model';

@Component({
  selector: 'jhi-poen-detail',
  templateUrl: './poen-detail.component.html'
})
export class PoenDetailComponent implements OnInit {
  poen: IPoen;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ poen }) => {
      this.poen = poen;
    });
  }

  previousState() {
    window.history.back();
  }
}
