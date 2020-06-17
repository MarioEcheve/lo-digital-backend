import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IComuna, Comuna } from 'app/shared/model/comuna.model';
import { ComunaService } from './comuna.service';
import { IRegion } from 'app/shared/model/region.model';
import { RegionService } from 'app/entities/region/region.service';

@Component({
  selector: 'jhi-comuna-update',
  templateUrl: './comuna-update.component.html'
})
export class ComunaUpdateComponent implements OnInit {
  isSaving = false;
  regions: IRegion[] = [];

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required]],
    region: []
  });

  constructor(
    protected comunaService: ComunaService,
    protected regionService: RegionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ comuna }) => {
      this.updateForm(comuna);

      this.regionService.query().subscribe((res: HttpResponse<IRegion[]>) => (this.regions = res.body || []));
    });
  }

  updateForm(comuna: IComuna): void {
    this.editForm.patchValue({
      id: comuna.id,
      nombre: comuna.nombre,
      region: comuna.region
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const comuna = this.createFromForm();
    if (comuna.id !== undefined) {
      this.subscribeToSaveResponse(this.comunaService.update(comuna));
    } else {
      this.subscribeToSaveResponse(this.comunaService.create(comuna));
    }
  }

  private createFromForm(): IComuna {
    return {
      ...new Comuna(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      region: this.editForm.get(['region'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IComuna>>): void {
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

  trackById(index: number, item: IRegion): any {
    return item.id;
  }
}
