import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { FolioService } from 'app/entities/folio/folio.service';
import { IFolio, Folio } from 'app/shared/model/folio.model';

describe('Service Tests', () => {
  describe('Folio Service', () => {
    let injector: TestBed;
    let service: FolioService;
    let httpMock: HttpTestingController;
    let elemDefault: IFolio;
    let expectedResult: IFolio | IFolio[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(FolioService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Folio(
        0,
        0,
        0,
        0,
        0,
        false,
        currentDate,
        false,
        false,
        false,
        currentDate,
        currentDate,
        currentDate,
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'image/png',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fechaRequerida: currentDate.format(DATE_TIME_FORMAT),
            fechaCreacion: currentDate.format(DATE_TIME_FORMAT),
            fechaModificacion: currentDate.format(DATE_TIME_FORMAT),
            fechaFirma: currentDate.format(DATE_TIME_FORMAT),
            fechaLectura: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Folio', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fechaRequerida: currentDate.format(DATE_TIME_FORMAT),
            fechaCreacion: currentDate.format(DATE_TIME_FORMAT),
            fechaModificacion: currentDate.format(DATE_TIME_FORMAT),
            fechaFirma: currentDate.format(DATE_TIME_FORMAT),
            fechaLectura: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaRequerida: currentDate,
            fechaCreacion: currentDate,
            fechaModificacion: currentDate,
            fechaFirma: currentDate,
            fechaLectura: currentDate
          },
          returnedFromService
        );

        service.create(new Folio()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Folio', () => {
        const returnedFromService = Object.assign(
          {
            idUsuarioCreador: 1,
            idUsuarioFirma: 1,
            idUsuarioLectura: 1,
            numeroFolio: 1,
            requiereRespuesta: true,
            fechaRequerida: currentDate.format(DATE_TIME_FORMAT),
            estadoLectura: true,
            estadoFolio: true,
            entidadCreacion: true,
            fechaCreacion: currentDate.format(DATE_TIME_FORMAT),
            fechaModificacion: currentDate.format(DATE_TIME_FORMAT),
            fechaFirma: currentDate.format(DATE_TIME_FORMAT),
            fechaLectura: currentDate.format(DATE_TIME_FORMAT),
            asunto: 'BBBBBB',
            anotacion: 'BBBBBB',
            pdfFirmado: 'BBBBBB',
            pdfLectura: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaRequerida: currentDate,
            fechaCreacion: currentDate,
            fechaModificacion: currentDate,
            fechaFirma: currentDate,
            fechaLectura: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Folio', () => {
        const returnedFromService = Object.assign(
          {
            idUsuarioCreador: 1,
            idUsuarioFirma: 1,
            idUsuarioLectura: 1,
            numeroFolio: 1,
            requiereRespuesta: true,
            fechaRequerida: currentDate.format(DATE_TIME_FORMAT),
            estadoLectura: true,
            estadoFolio: true,
            entidadCreacion: true,
            fechaCreacion: currentDate.format(DATE_TIME_FORMAT),
            fechaModificacion: currentDate.format(DATE_TIME_FORMAT),
            fechaFirma: currentDate.format(DATE_TIME_FORMAT),
            fechaLectura: currentDate.format(DATE_TIME_FORMAT),
            asunto: 'BBBBBB',
            anotacion: 'BBBBBB',
            pdfFirmado: 'BBBBBB',
            pdfLectura: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaRequerida: currentDate,
            fechaCreacion: currentDate,
            fechaModificacion: currentDate,
            fechaFirma: currentDate,
            fechaLectura: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Folio', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
