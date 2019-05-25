import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IOdgovor, Odgovor } from 'app/shared/model/odgovor.model';
import { OdgovorService } from './odgovor.service';
import { IPojasnjenje } from 'app/shared/model/pojasnjenje.model';
import { PojasnjenjeService } from 'app/entities/pojasnjenje';
import { IPitanje } from 'app/shared/model/pitanje.model';
import { PitanjeService } from 'app/entities/pitanje';

@Component({
  selector: 'jhi-odgovor-update',
  templateUrl: './odgovor-update.component.html'
})
export class OdgovorUpdateComponent implements OnInit {
  odgovor: IOdgovor;
  isSaving: boolean;

  pojasnjenjes: IPojasnjenje[];

  pitanjes: IPitanje[];

  editForm = this.fb.group({
    id: [],
    tekst: [null, [Validators.maxLength(64)]],
    tacan: [],
    pojasnjenjeId: [],
    pitanjeId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected odgovorService: OdgovorService,
    protected pojasnjenjeService: PojasnjenjeService,
    protected pitanjeService: PitanjeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ odgovor }) => {
      this.updateForm(odgovor);
      this.odgovor = odgovor;
    });
    this.pojasnjenjeService
      .query({ 'odgovorId.specified': 'false' })
      .pipe(
        filter((mayBeOk: HttpResponse<IPojasnjenje[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPojasnjenje[]>) => response.body)
      )
      .subscribe(
        (res: IPojasnjenje[]) => {
          if (!this.odgovor.pojasnjenjeId) {
            this.pojasnjenjes = res;
          } else {
            this.pojasnjenjeService
              .find(this.odgovor.pojasnjenjeId)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IPojasnjenje>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IPojasnjenje>) => subResponse.body)
              )
              .subscribe(
                (subRes: IPojasnjenje) => (this.pojasnjenjes = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.pitanjeService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPitanje[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPitanje[]>) => response.body)
      )
      .subscribe((res: IPitanje[]) => (this.pitanjes = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(odgovor: IOdgovor) {
    this.editForm.patchValue({
      id: odgovor.id,
      tekst: odgovor.tekst,
      tacan: odgovor.tacan,
      pojasnjenjeId: odgovor.pojasnjenjeId,
      pitanjeId: odgovor.pitanjeId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const odgovor = this.createFromForm();
    if (odgovor.id !== undefined) {
      this.subscribeToSaveResponse(this.odgovorService.update(odgovor));
    } else {
      this.subscribeToSaveResponse(this.odgovorService.create(odgovor));
    }
  }

  private createFromForm(): IOdgovor {
    const entity = {
      ...new Odgovor(),
      id: this.editForm.get(['id']).value,
      tekst: this.editForm.get(['tekst']).value,
      tacan: this.editForm.get(['tacan']).value,
      pojasnjenjeId: this.editForm.get(['pojasnjenjeId']).value,
      pitanjeId: this.editForm.get(['pitanjeId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOdgovor>>) {
    result.subscribe((res: HttpResponse<IOdgovor>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackPojasnjenjeById(index: number, item: IPojasnjenje) {
    return item.id;
  }

  trackPitanjeById(index: number, item: IPitanje) {
    return item.id;
  }
}
