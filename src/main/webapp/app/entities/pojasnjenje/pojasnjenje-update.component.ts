import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IPojasnjenje, Pojasnjenje } from 'app/shared/model/pojasnjenje.model';
import { PojasnjenjeService } from './pojasnjenje.service';
import { IOdgovor } from 'app/shared/model/odgovor.model';
import { OdgovorService } from 'app/entities/odgovor';

@Component({
  selector: 'jhi-pojasnjenje-update',
  templateUrl: './pojasnjenje-update.component.html'
})
export class PojasnjenjeUpdateComponent implements OnInit {
  pojasnjenje: IPojasnjenje;
  isSaving: boolean;

  odgovors: IOdgovor[];

  editForm = this.fb.group({
    id: [],
    tekst: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected pojasnjenjeService: PojasnjenjeService,
    protected odgovorService: OdgovorService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ pojasnjenje }) => {
      this.updateForm(pojasnjenje);
      this.pojasnjenje = pojasnjenje;
    });
    this.odgovorService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IOdgovor[]>) => mayBeOk.ok),
        map((response: HttpResponse<IOdgovor[]>) => response.body)
      )
      .subscribe((res: IOdgovor[]) => (this.odgovors = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(pojasnjenje: IPojasnjenje) {
    this.editForm.patchValue({
      id: pojasnjenje.id,
      tekst: pojasnjenje.tekst
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  setFileData(event, field: string, isImage) {
    return new Promise((resolve, reject) => {
      if (event && event.target && event.target.files && event.target.files[0]) {
        const file = event.target.files[0];
        if (isImage && !/^image\//.test(file.type)) {
          reject(`File was expected to be an image but was found to be ${file.type}`);
        } else {
          const filedContentType: string = field + 'ContentType';
          this.dataUtils.toBase64(file, base64Data => {
            this.editForm.patchValue({
              [field]: base64Data,
              [filedContentType]: file.type
            });
          });
        }
      } else {
        reject(`Base64 data was not set as file could not be extracted from passed parameter: ${event}`);
      }
    }).then(
      () => console.log('blob added'), // sucess
      this.onError
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const pojasnjenje = this.createFromForm();
    if (pojasnjenje.id !== undefined) {
      this.subscribeToSaveResponse(this.pojasnjenjeService.update(pojasnjenje));
    } else {
      this.subscribeToSaveResponse(this.pojasnjenjeService.create(pojasnjenje));
    }
  }

  private createFromForm(): IPojasnjenje {
    const entity = {
      ...new Pojasnjenje(),
      id: this.editForm.get(['id']).value,
      tekst: this.editForm.get(['tekst']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPojasnjenje>>) {
    result.subscribe((res: HttpResponse<IPojasnjenje>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackOdgovorById(index: number, item: IOdgovor) {
    return item.id;
  }
}
