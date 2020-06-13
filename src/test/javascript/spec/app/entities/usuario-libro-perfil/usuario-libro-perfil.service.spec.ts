import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { UsuarioLibroPerfilService } from 'app/entities/usuario-libro-perfil/usuario-libro-perfil.service';
import { IUsuarioLibroPerfil, UsuarioLibroPerfil } from 'app/shared/model/usuario-libro-perfil.model';

describe('Service Tests', () => {
  describe('UsuarioLibroPerfil Service', () => {
    let injector: TestBed;
    let service: UsuarioLibroPerfilService;
    let httpMock: HttpTestingController;
    let elemDefault: IUsuarioLibroPerfil;
    let expectedResult: IUsuarioLibroPerfil | IUsuarioLibroPerfil[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(UsuarioLibroPerfilService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new UsuarioLibroPerfil(0, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a UsuarioLibroPerfil', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new UsuarioLibroPerfil()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a UsuarioLibroPerfil', () => {
        const returnedFromService = Object.assign(
          {
            nombre: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of UsuarioLibroPerfil', () => {
        const returnedFromService = Object.assign(
          {
            nombre: 'BBBBBB'
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

      it('should delete a UsuarioLibroPerfil', () => {
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
