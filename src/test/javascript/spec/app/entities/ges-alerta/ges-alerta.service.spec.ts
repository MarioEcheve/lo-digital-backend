import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { GesAlertaService } from 'app/entities/ges-alerta/ges-alerta.service';
import { IGesAlerta, GesAlerta } from 'app/shared/model/ges-alerta.model';

describe('Service Tests', () => {
  describe('GesAlerta Service', () => {
    let injector: TestBed;
    let service: GesAlertaService;
    let httpMock: HttpTestingController;
    let elemDefault: IGesAlerta;
    let expectedResult: IGesAlerta | IGesAlerta[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(GesAlertaService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new GesAlerta(0, 'AAAAAAA', currentDate, currentDate, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fechaAlerta: currentDate.format(DATE_TIME_FORMAT),
            fechaCreacion: currentDate.format(DATE_TIME_FORMAT),
            fechaModificacion: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a GesAlerta', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fechaAlerta: currentDate.format(DATE_TIME_FORMAT),
            fechaCreacion: currentDate.format(DATE_TIME_FORMAT),
            fechaModificacion: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaAlerta: currentDate,
            fechaCreacion: currentDate,
            fechaModificacion: currentDate
          },
          returnedFromService
        );

        service.create(new GesAlerta()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a GesAlerta', () => {
        const returnedFromService = Object.assign(
          {
            nota: 'BBBBBB',
            fechaAlerta: currentDate.format(DATE_TIME_FORMAT),
            fechaCreacion: currentDate.format(DATE_TIME_FORMAT),
            fechaModificacion: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaAlerta: currentDate,
            fechaCreacion: currentDate,
            fechaModificacion: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of GesAlerta', () => {
        const returnedFromService = Object.assign(
          {
            nota: 'BBBBBB',
            fechaAlerta: currentDate.format(DATE_TIME_FORMAT),
            fechaCreacion: currentDate.format(DATE_TIME_FORMAT),
            fechaModificacion: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaAlerta: currentDate,
            fechaCreacion: currentDate,
            fechaModificacion: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a GesAlerta', () => {
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
