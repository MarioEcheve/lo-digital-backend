import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { UsuarioService } from 'app/entities/usuario/usuario.service';
import { IUsuario, Usuario } from 'app/shared/model/usuario.model';

describe('Service Tests', () => {
  describe('Usuario Service', () => {
    let injector: TestBed;
    let service: UsuarioService;
    let httpMock: HttpTestingController;
    let elemDefault: IUsuario;
    let expectedResult: IUsuario | IUsuario[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(UsuarioService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Usuario(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        currentDate
      );
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

      it('should create a Usuario', () => {
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

        service.create(new Usuario()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Usuario', () => {
        const returnedFromService = Object.assign(
          {
            rut: 'BBBBBB',
            nombre: 'BBBBBB',
            apellidoPaterno: 'BBBBBB',
            apellidoMaterno: 'BBBBBB',
            profesionOficio: 'BBBBBB',
            emailPrincipal: 'BBBBBB',
            emailSecundario: 'BBBBBB',
            telefonoPrincipal: 'BBBBBB',
            telefonoSecundario: 'BBBBBB',
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

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Usuario', () => {
        const returnedFromService = Object.assign(
          {
            rut: 'BBBBBB',
            nombre: 'BBBBBB',
            apellidoPaterno: 'BBBBBB',
            apellidoMaterno: 'BBBBBB',
            profesionOficio: 'BBBBBB',
            emailPrincipal: 'BBBBBB',
            emailSecundario: 'BBBBBB',
            telefonoPrincipal: 'BBBBBB',
            telefonoSecundario: 'BBBBBB',
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

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Usuario', () => {
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
