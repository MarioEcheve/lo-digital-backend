import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { GesFavoritoService } from 'app/entities/ges-favorito/ges-favorito.service';
import { IGesFavorito, GesFavorito } from 'app/shared/model/ges-favorito.model';

describe('Service Tests', () => {
  describe('GesFavorito Service', () => {
    let injector: TestBed;
    let service: GesFavoritoService;
    let httpMock: HttpTestingController;
    let elemDefault: IGesFavorito;
    let expectedResult: IGesFavorito | IGesFavorito[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(GesFavoritoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new GesFavorito(0, 'AAAAAAA', currentDate, currentDate, false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
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

      it('should create a GesFavorito', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fechaCreacion: currentDate.format(DATE_TIME_FORMAT),
            fechaModificacion: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fechaCreacion: currentDate,
            fechaModificacion: currentDate
          },
          returnedFromService
        );

        service.create(new GesFavorito()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a GesFavorito', () => {
        const returnedFromService = Object.assign(
          {
            nota: 'BBBBBB',
            fechaCreacion: currentDate.format(DATE_TIME_FORMAT),
            fechaModificacion: currentDate.format(DATE_TIME_FORMAT),
            favorito: true
          },
          elemDefault
        );

        const expected = Object.assign(
          {
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

      it('should return a list of GesFavorito', () => {
        const returnedFromService = Object.assign(
          {
            nota: 'BBBBBB',
            fechaCreacion: currentDate.format(DATE_TIME_FORMAT),
            fechaModificacion: currentDate.format(DATE_TIME_FORMAT),
            favorito: true
          },
          elemDefault
        );

        const expected = Object.assign(
          {
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

      it('should delete a GesFavorito', () => {
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
