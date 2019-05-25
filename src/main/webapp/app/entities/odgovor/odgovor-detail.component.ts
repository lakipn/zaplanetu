import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOdgovor } from 'app/shared/model/odgovor.model';

@Component({
  selector: 'jhi-odgovor-detail',
  templateUrl: './odgovor-detail.component.html'
})
export class OdgovorDetailComponent implements OnInit {
  odgovor: IOdgovor;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ odgovor }) => {
      this.odgovor = odgovor;
    });
  }

  previousState() {
    window.history.back();
  }
}
