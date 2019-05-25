import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IPitanje, Pitanje } from 'app/shared/model/pitanje.model';
import { PitanjeService } from './pitanje.service';
import { ISekcija } from 'app/shared/model/sekcija.model';
import { SekcijaService } from 'app/entities/sekcija';

@Component({
  selector: 'jhi-pitanje-update',
  templateUrl: './pitanje-update.component.html'
})
export class PitanjeUpdateComponent implements OnInit {
  pitanje: IPitanje;
  isSaving: boolean;

  sekcijas: ISekcija[];

  editForm = this.fb.group({
    id: [],
    tekst: [null, [Validators.maxLength(256)]],
    redniBroj: [],
    sekcijaId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected pitanjeService: PitanjeService,
    protected sekcijaService: SekcijaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ pitanje }) => {
      this.updateForm(pitanje);
      this.pitanje = pitanje;
    });
    this.sekcijaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ISekcija[]>) => mayBeOk.ok),
        map((response: HttpResponse<ISekcija[]>) => response.body)
      )
      .subscribe((res: ISekcija[]) => (this.sekcijas = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(pitanje: IPitanje) {
    this.editForm.patchValue({
      id: pitanje.id,
      tekst: pitanje.tekst,
      redniBroj: pitanje.redniBroj,
      sekcijaId: pitanje.sekcijaId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const pitanje = this.createFromForm();
    if (pitanje.id !== undefined) {
      this.subscribeToSaveResponse(this.pitanjeService.update(pitanje));
    } else {
      this.subscribeToSaveResponse(this.pitanjeService.create(pitanje));
    }
  }

  private createFromForm(): IPitanje {
    const entity = {
      ...new Pitanje(),
      id: this.editForm.get(['id']).value,
      tekst: this.editForm.get(['tekst']).value,
      redniBroj: this.editForm.get(['redniBroj']).value,
      sekcijaId: this.editForm.get(['sekcijaId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPitanje>>) {
    result.subscribe((res: HttpResponse<IPitanje>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackSekcijaById(index: number, item: ISekcija) {
    return item.id;
  }
}
