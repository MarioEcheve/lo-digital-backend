import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IContrato, Contrato } from 'app/shared/model/contrato.model';
import { ContratoService } from './contrato.service';
import { IDependencia } from 'app/shared/model/dependencia.model';
import { DependenciaService } from 'app/entities/dependencia/dependencia.service';
import { IRegion } from 'app/shared/model/region.model';
import { RegionService } from 'app/entities/region/region.service';
import { ITipoContrato } from 'app/shared/model/tipo-contrato.model';
import { TipoContratoService } from 'app/entities/tipo-contrato/tipo-contrato.service';
import { IModalidad } from 'app/shared/model/modalidad.model';
import { ModalidadService } from 'app/entities/modalidad/modalidad.service';
import { IComuna } from 'app/shared/model/comuna.model';
import { ComunaService } from 'app/entities/comuna/comuna.service';
import { ITipoMoneda } from 'app/shared/model/tipo-moneda.model';
import { TipoMonedaService } from 'app/entities/tipo-moneda/tipo-moneda.service';
import { ITipoMonto } from 'app/shared/model/tipo-monto.model';
import { TipoMontoService } from 'app/entities/tipo-monto/tipo-monto.service';
import { IEstadoServicio } from 'app/shared/model/estado-servicio.model';
import { EstadoServicioService } from 'app/entities/estado-servicio/estado-servicio.service';

type SelectableEntity = IDependencia | IRegion | ITipoContrato | IModalidad | IComuna | ITipoMoneda | ITipoMonto | IEstadoServicio;

@Component({
  selector: 'jhi-contrato-update',
  templateUrl: './contrato-update.component.html'
})
export class ContratoUpdateComponent implements OnInit {
  isSaving = false;
  dependencias: IDependencia[] = [];
  regions: IRegion[] = [];
  tipocontratoes: ITipoContrato[] = [];
  modalidads: IModalidad[] = [];
  comunas: IComuna[] = [];
  tipomonedas: ITipoMoneda[] = [];
  tipomontos: ITipoMonto[] = [];
  estadoservicios: IEstadoServicio[] = [];

  editForm = this.fb.group({
    id: [],
    fechaInicioServicio: [],
    fechaTerminoServicio: [],
    fechaTerminoAcceso: [],
    observacionesServicio: [null, [Validators.maxLength(250)]],
    codigo: [null, [Validators.required, Validators.maxLength(20)]],
    nombre: [null, [Validators.required, Validators.maxLength(60)]],
    descripcion: [null, [Validators.maxLength(200)]],
    tipoOtro: [null, [Validators.maxLength(25)]],
    modalidadOtra: [null, [Validators.maxLength(25)]],
    direccion: [null, [Validators.required, Validators.maxLength(100)]],
    monto: [],
    fechaInicio: [],
    fechaTermino: [],
    observaciones: [null, [Validators.maxLength(200)]],
    nombreContacto: [null, [Validators.maxLength(50)]],
    cargo: [null, [Validators.maxLength(50)]],
    telefonoContacto: [null, [Validators.maxLength(50)]],
    telefonoContactoSecundario: [null, [Validators.maxLength(50)]],
    emailContacto: [null, [Validators.maxLength(50)]],
    idDependenciaContratista: [],
    creaLibroAdminMan: [],
    creaLibroAdminCon: [],
    actualizarContratoAdminMan: [],
    actualizarContratoAdminCon: [],
    dependenciaMandante: [],
    region: [],
    tipoContrato: [],
    modalidad: [],
    comuna: [],
    tipoMoneda: [],
    tipoMonto: [],
    estadoServicio: []
  });

  constructor(
    protected contratoService: ContratoService,
    protected dependenciaService: DependenciaService,
    protected regionService: RegionService,
    protected tipoContratoService: TipoContratoService,
    protected modalidadService: ModalidadService,
    protected comunaService: ComunaService,
    protected tipoMonedaService: TipoMonedaService,
    protected tipoMontoService: TipoMontoService,
    protected estadoServicioService: EstadoServicioService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contrato }) => {
      if (!contrato.id) {
        const today = moment().startOf('day');
        contrato.fechaInicioServicio = today;
        contrato.fechaTerminoServicio = today;
        contrato.fechaTerminoAcceso = today;
        contrato.fechaInicio = today;
        contrato.fechaTermino = today;
      }

      this.updateForm(contrato);

      this.dependenciaService.query().subscribe((res: HttpResponse<IDependencia[]>) => (this.dependencias = res.body || []));

      this.regionService.query().subscribe((res: HttpResponse<IRegion[]>) => (this.regions = res.body || []));

      this.tipoContratoService.query().subscribe((res: HttpResponse<ITipoContrato[]>) => (this.tipocontratoes = res.body || []));

      this.modalidadService.query().subscribe((res: HttpResponse<IModalidad[]>) => (this.modalidads = res.body || []));

      this.comunaService.query().subscribe((res: HttpResponse<IComuna[]>) => (this.comunas = res.body || []));

      this.tipoMonedaService.query().subscribe((res: HttpResponse<ITipoMoneda[]>) => (this.tipomonedas = res.body || []));

      this.tipoMontoService.query().subscribe((res: HttpResponse<ITipoMonto[]>) => (this.tipomontos = res.body || []));

      this.estadoServicioService.query().subscribe((res: HttpResponse<IEstadoServicio[]>) => (this.estadoservicios = res.body || []));
    });
  }

  updateForm(contrato: IContrato): void {
    this.editForm.patchValue({
      id: contrato.id,
      fechaInicioServicio: contrato.fechaInicioServicio ? contrato.fechaInicioServicio.format(DATE_TIME_FORMAT) : null,
      fechaTerminoServicio: contrato.fechaTerminoServicio ? contrato.fechaTerminoServicio.format(DATE_TIME_FORMAT) : null,
      fechaTerminoAcceso: contrato.fechaTerminoAcceso ? contrato.fechaTerminoAcceso.format(DATE_TIME_FORMAT) : null,
      observacionesServicio: contrato.observacionesServicio,
      codigo: contrato.codigo,
      nombre: contrato.nombre,
      descripcion: contrato.descripcion,
      tipoOtro: contrato.tipoOtro,
      modalidadOtra: contrato.modalidadOtra,
      direccion: contrato.direccion,
      monto: contrato.monto,
      fechaInicio: contrato.fechaInicio ? contrato.fechaInicio.format(DATE_TIME_FORMAT) : null,
      fechaTermino: contrato.fechaTermino ? contrato.fechaTermino.format(DATE_TIME_FORMAT) : null,
      observaciones: contrato.observaciones,
      nombreContacto: contrato.nombreContacto,
      cargo: contrato.cargo,
      telefonoContacto: contrato.telefonoContacto,
      telefonoContactoSecundario: contrato.telefonoContactoSecundario,
      emailContacto: contrato.emailContacto,
      idDependenciaContratista: contrato.idDependenciaContratista,
      creaLibroAdminMan: contrato.creaLibroAdminMan,
      creaLibroAdminCon: contrato.creaLibroAdminCon,
      actualizarContratoAdminMan: contrato.actualizarContratoAdminMan,
      actualizarContratoAdminCon: contrato.actualizarContratoAdminCon,
      dependenciaMandante: contrato.dependenciaMandante,
      region: contrato.region,
      tipoContrato: contrato.tipoContrato,
      modalidad: contrato.modalidad,
      comuna: contrato.comuna,
      tipoMoneda: contrato.tipoMoneda,
      tipoMonto: contrato.tipoMonto,
      estadoServicio: contrato.estadoServicio
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contrato = this.createFromForm();
    if (contrato.id !== undefined) {
      this.subscribeToSaveResponse(this.contratoService.update(contrato));
    } else {
      this.subscribeToSaveResponse(this.contratoService.create(contrato));
    }
  }

  private createFromForm(): IContrato {
    return {
      ...new Contrato(),
      id: this.editForm.get(['id'])!.value,
      fechaInicioServicio: this.editForm.get(['fechaInicioServicio'])!.value
        ? moment(this.editForm.get(['fechaInicioServicio'])!.value, DATE_TIME_FORMAT)
        : undefined,
      fechaTerminoServicio: this.editForm.get(['fechaTerminoServicio'])!.value
        ? moment(this.editForm.get(['fechaTerminoServicio'])!.value, DATE_TIME_FORMAT)
        : undefined,
      fechaTerminoAcceso: this.editForm.get(['fechaTerminoAcceso'])!.value
        ? moment(this.editForm.get(['fechaTerminoAcceso'])!.value, DATE_TIME_FORMAT)
        : undefined,
      observacionesServicio: this.editForm.get(['observacionesServicio'])!.value,
      codigo: this.editForm.get(['codigo'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      descripcion: this.editForm.get(['descripcion'])!.value,
      tipoOtro: this.editForm.get(['tipoOtro'])!.value,
      modalidadOtra: this.editForm.get(['modalidadOtra'])!.value,
      direccion: this.editForm.get(['direccion'])!.value,
      monto: this.editForm.get(['monto'])!.value,
      fechaInicio: this.editForm.get(['fechaInicio'])!.value
        ? moment(this.editForm.get(['fechaInicio'])!.value, DATE_TIME_FORMAT)
        : undefined,
      fechaTermino: this.editForm.get(['fechaTermino'])!.value
        ? moment(this.editForm.get(['fechaTermino'])!.value, DATE_TIME_FORMAT)
        : undefined,
      observaciones: this.editForm.get(['observaciones'])!.value,
      nombreContacto: this.editForm.get(['nombreContacto'])!.value,
      cargo: this.editForm.get(['cargo'])!.value,
      telefonoContacto: this.editForm.get(['telefonoContacto'])!.value,
      telefonoContactoSecundario: this.editForm.get(['telefonoContactoSecundario'])!.value,
      emailContacto: this.editForm.get(['emailContacto'])!.value,
      idDependenciaContratista: this.editForm.get(['idDependenciaContratista'])!.value,
      creaLibroAdminMan: this.editForm.get(['creaLibroAdminMan'])!.value,
      creaLibroAdminCon: this.editForm.get(['creaLibroAdminCon'])!.value,
      actualizarContratoAdminMan: this.editForm.get(['actualizarContratoAdminMan'])!.value,
      actualizarContratoAdminCon: this.editForm.get(['actualizarContratoAdminCon'])!.value,
      dependenciaMandante: this.editForm.get(['dependenciaMandante'])!.value,
      region: this.editForm.get(['region'])!.value,
      tipoContrato: this.editForm.get(['tipoContrato'])!.value,
      modalidad: this.editForm.get(['modalidad'])!.value,
      comuna: this.editForm.get(['comuna'])!.value,
      tipoMoneda: this.editForm.get(['tipoMoneda'])!.value,
      tipoMonto: this.editForm.get(['tipoMonto'])!.value,
      estadoServicio: this.editForm.get(['estadoServicio'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContrato>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
