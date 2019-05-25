import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IPoen, Poen } from 'app/shared/model/poen.model';
import { PoenService } from './poen.service';
import { IUser, UserService } from 'app/core';
import { IDrzava } from 'app/shared/model/drzava.model';
import { DrzavaService } from 'app/entities/drzava';

@Component({
  selector: 'jhi-poen-update',
  templateUrl: './poen-update.component.html'
})
export class PoenUpdateComponent implements OnInit {
  poen: IPoen;
  isSaving: boolean;

  users: IUser[];

  drzavas: IDrzava[];

  editForm = this.fb.group({
    id: [],
    tip: [],
    korisnikId: [],
    drzavaId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected poenService: PoenService,
    protected userService: UserService,
    protected drzavaService: DrzavaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ poen }) => {
      this.updateForm(poen);
      this.poen = poen;
    });
    this.userService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUser[]>) => response.body)
      )
      .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.drzavaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IDrzava[]>) => mayBeOk.ok),
        map((response: HttpResponse<IDrzava[]>) => response.body)
      )
      .subscribe((res: IDrzava[]) => (this.drzavas = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(poen: IPoen) {
    this.editForm.patchValue({
      id: poen.id,
      tip: poen.tip,
      korisnikId: poen.korisnikId,
      drzavaId: poen.drzavaId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const poen = this.createFromForm();
    if (poen.id !== undefined) {
      this.subscribeToSaveResponse(this.poenService.update(poen));
    } else {
      this.subscribeToSaveResponse(this.poenService.create(poen));
    }
  }

  private createFromForm(): IPoen {
    const entity = {
      ...new Poen(),
      id: this.editForm.get(['id']).value,
      tip: this.editForm.get(['tip']).value,
      korisnikId: this.editForm.get(['korisnikId']).value,
      drzavaId: this.editForm.get(['drzavaId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPoen>>) {
    result.subscribe((res: HttpResponse<IPoen>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackUserById(index: number, item: IUser) {
    return item.id;
  }

  trackDrzavaById(index: number, item: IDrzava) {
    return item.id;
  }
}
