import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { FolioReferenciaService } from 'app/entities/folio-referencia/folio-referencia.service';
import { IFolioReferencia, FolioReferencia } from 'app/shared/model/folio-referencia.model';

describe('Service Tests', () => {
  describe('FolioReferencia Service', () => {
    let injector: TestBed;
    let service: FolioReferenciaService;
    let httpMock: HttpTestingController;
    let elemDefault: IFolioReferencia;
    let expectedResult: IFolioReferencia | IFolioReferencia[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(FolioReferenciaService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new FolioReferencia(0, 'AAAAAAA', 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a FolioReferencia', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new FolioReferencia()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a FolioReferencia', () => {
        const returnedFromService = Object.assign(
          {
            asunto: 'BBBBBB',
            idFolioOrigen: 1,
            idFolioReferencia: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of FolioReferencia', () => {
        const returnedFromService = Object.assign(
          {
            asunto: 'BBBBBB',
            idFolioOrigen: 1,
            idFolioReferencia: 1
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

      it('should delete a FolioReferencia', () => {
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
