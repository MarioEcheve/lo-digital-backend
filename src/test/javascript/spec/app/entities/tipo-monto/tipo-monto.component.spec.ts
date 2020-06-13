import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BackendTestModule } from '../../../test.module';
import { TipoMontoComponent } from 'app/entities/tipo-monto/tipo-monto.component';
import { TipoMontoService } from 'app/entities/tipo-monto/tipo-monto.service';
import { TipoMonto } from 'app/shared/model/tipo-monto.model';

describe('Component Tests', () => {
  describe('TipoMonto Management Component', () => {
    let comp: TipoMontoComponent;
    let fixture: ComponentFixture<TipoMontoComponent>;
    let service: TipoMontoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [TipoMontoComponent]
      })
        .overrideTemplate(TipoMontoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipoMontoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoMontoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TipoMonto(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.tipoMontos && comp.tipoMontos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
