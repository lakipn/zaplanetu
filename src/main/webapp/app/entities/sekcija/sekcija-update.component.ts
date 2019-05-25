import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { ISekcija, Sekcija } from 'app/shared/model/sekcija.model';
import { SekcijaService } from './sekcija.service';

@Component({
  selector: 'jhi-sekcija-update',
  templateUrl: './sekcija-update.component.html'
})
export class SekcijaUpdateComponent implements OnInit {
  sekcija: ISekcija;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    naziv: [null, [Validators.maxLength(64)]],
    slika: [],
    slikaContentType: [],
    pojasnjenje: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected sekcijaService: SekcijaService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ sekcija }) => {
      this.updateForm(sekcija);
      this.sekcija = sekcija;
    });
  }

  updateForm(sekcija: ISekcija) {
    this.editForm.patchValue({
      id: sekcija.id,
      naziv: sekcija.naziv,
      slika: sekcija.slika,
      slikaContentType: sekcija.slikaContentType,
      pojasnjenje: sekcija.pojasnjenje
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

  clearInputImage(field: string, fieldContentType: string, idInput: string) {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const sekcija = this.createFromForm();
    if (sekcija.id !== undefined) {
      this.subscribeToSaveResponse(this.sekcijaService.update(sekcija));
    } else {
      this.subscribeToSaveResponse(this.sekcijaService.create(sekcija));
    }
  }

  private createFromForm(): ISekcija {
    const entity = {
      ...new Sekcija(),
      id: this.editForm.get(['id']).value,
      naziv: this.editForm.get(['naziv']).value,
      slikaContentType: this.editForm.get(['slikaContentType']).value,
      slika: this.editForm.get(['slika']).value,
      pojasnjenje: this.editForm.get(['pojasnjenje']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISekcija>>) {
    result.subscribe((res: HttpResponse<ISekcija>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
}
