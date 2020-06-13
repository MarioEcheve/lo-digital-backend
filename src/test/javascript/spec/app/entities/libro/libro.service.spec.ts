import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { LibroService } from 'app/entities/libro/libro.service';
import { ILibro, Libro } from 'app/shared/model/libro.model';

describe('Service Tests', () => {
  describe('Libro Service', () => {
    let injector: TestBed;
    let service: LibroService;
    let httpMock: HttpTestingController;
    let elemDefault: ILibro;
    let expectedResult: ILibro | ILibro[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(LibroService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Libro(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        currentDate,
        currentDate,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fechaCreacion: currentDate.format(DATE_TIME_FORMAT),
            fechaApertura: currentDate.format(DATE_TIME_FORMAT),
            fechaCierre: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Libro', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fechaCreacion: currentDate.format(DATE_TIME_FORMAT),
            fechaApertura: currentDate.format(DATE_TIME_FORMAT),
            fechaCierre: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaCreacion: currentDate,
            fechaApertura: currentDate,
            fechaCierre: currentDate
          },
          returnedFromService
        );

        service.create(new Libro()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Libro', () => {
        const returnedFromService = Object.assign(
          {
            codigo: 'BBBBBB',
            nombre: 'BBBBBB',
            descripcion: 'BBBBBB',
            fechaCreacion: currentDate.format(DATE_TIME_FORMAT),
            fechaApertura: currentDate.format(DATE_TIME_FORMAT),
            fechaCierre: currentDate.format(DATE_TIME_FORMAT),
            aperturaMandante: true,
            aperturaContratista: true,
            escrituraMandante: true,
            escrituraContratista: true,
            cierreMandante: true,
            cierreContratista: true,
            lecturaMandante: true,
            lecturaContratista: true
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaCreacion: currentDate,
            fechaApertura: currentDate,
            fechaCierre: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Libro', () => {
        const returnedFromService = Object.assign(
          {
            codigo: 'BBBBBB',
            nombre: 'BBBBBB',
            descripcion: 'BBBBBB',
            fechaCreacion: currentDate.format(DATE_TIME_FORMAT),
            fechaApertura: currentDate.format(DATE_TIME_FORMAT),
            fechaCierre: currentDate.format(DATE_TIME_FORMAT),
            aperturaMandante: true,
            aperturaContratista: true,
            escrituraMandante: true,
            escrituraContratista: true,
            cierreMandante: true,
            cierreContratista: true,
            lecturaMandante: true,
            lecturaContratista: true
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaCreacion: currentDate,
            fechaApertura: currentDate,
            fechaCierre: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Libro', () => {
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
