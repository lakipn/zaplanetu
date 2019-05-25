import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPitanje } from 'app/shared/model/pitanje.model';

@Component({
  selector: 'jhi-pitanje-detail',
  templateUrl: './pitanje-detail.component.html'
})
export class PitanjeDetailComponent implements OnInit {
  pitanje: IPitanje;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pitanje }) => {
      this.pitanje = pitanje;
    });
  }

  previousState() {
    window.history.back();
  }
}
