import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TipoFolioService } from 'app/entities/tipo-folio/tipo-folio.service';
import { ITipoFolio, TipoFolio } from 'app/shared/model/tipo-folio.model';

describe('Service Tests', () => {
  describe('TipoFolio Service', () => {
    let injector: TestBed;
    let service: TipoFolioService;
    let httpMock: HttpTestingController;
    let elemDefault: ITipoFolio;
    let expectedResult: ITipoFolio | ITipoFolio[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TipoFolioService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new TipoFolio(0, 'AAAAAAA', false, false, false, false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a TipoFolio', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new TipoFolio()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TipoFolio', () => {
        const returnedFromService = Object.assign(
          {
            nombre: 'BBBBBB',
            visibleMaestro: true,
            visibleAuxliar: true,
            visibleMandante: true,
            visibleContratista: true
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of TipoFolio', () => {
        const returnedFromService = Object.assign(
          {
            nombre: 'BBBBBB',
            visibleMaestro: true,
            visibleAuxliar: true,
            visibleMandante: true,
            visibleContratista: true
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a TipoFolio', () => {
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
