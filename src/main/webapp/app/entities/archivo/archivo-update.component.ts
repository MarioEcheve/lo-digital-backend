import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IArchivo, Archivo } from 'app/shared/model/archivo.model';
import { ArchivoService } from './archivo.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IFolio } from 'app/shared/model/folio.model';
import { FolioService } from 'app/entities/folio/folio.service';

@Component({
  selector: 'jhi-archivo-update',
  templateUrl: './archivo-update.component.html'
})
export class ArchivoUpdateComponent implements OnInit {
  isSaving = false;
  folios: IFolio[] = [];

  editForm = this.fb.group({
    id: [],
    archivo: [null, [Validators.required]],
    archivoContentType: [],
    descripcion: [null, [Validators.maxLength(10485760)]],
    size: [null, [Validators.required]],
    folio: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected archivoService: ArchivoService,
    protected folioService: FolioService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ archivo }) => {
      this.updateForm(archivo);

      this.folioService.query().subscribe((res: HttpResponse<IFolio[]>) => (this.folios = res.body || []));
    });
  }

  updateForm(archivo: IArchivo): void {
    this.editForm.patchValue({
      id: archivo.id,
      archivo: archivo.archivo,
      archivoContentType: archivo.archivoContentType,
      descripcion: archivo.descripcion,
      size: archivo.size,
      folio: archivo.folio
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('backendApp.error', { message: err.message })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const archivo = this.createFromForm();
    if (archivo.id !== undefined) {
      this.subscribeToSaveResponse(this.archivoService.update(archivo));
    } else {
      this.subscribeToSaveResponse(this.archivoService.create(archivo));
    }
  }

  private createFromForm(): IArchivo {
    return {
      ...new Archivo(),
      id: this.editForm.get(['id'])!.value,
      archivoContentType: this.editForm.get(['archivoContentType'])!.value,
      archivo: this.editForm.get(['archivo'])!.value,
      descripcion: this.editForm.get(['descripcion'])!.value,
      size: this.editForm.get(['size'])!.value,
      folio: this.editForm.get(['folio'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IArchivo>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IFolio): any {
    return item.id;
  }
}
