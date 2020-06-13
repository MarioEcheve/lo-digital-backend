import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BackendTestModule } from '../../../test.module';
import { TipoContratoComponent } from 'app/entities/tipo-contrato/tipo-contrato.component';
import { TipoContratoService } from 'app/entities/tipo-contrato/tipo-contrato.service';
import { TipoContrato } from 'app/shared/model/tipo-contrato.model';

describe('Component Tests', () => {
  describe('TipoContrato Management Component', () => {
    let comp: TipoContratoComponent;
    let fixture: ComponentFixture<TipoContratoComponent>;
    let service: TipoContratoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [TipoContratoComponent]
      })
        .overrideTemplate(TipoContratoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipoContratoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoContratoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TipoContrato(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.tipoContratoes && comp.tipoContratoes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
