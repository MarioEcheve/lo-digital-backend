import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BackendTestModule } from '../../../test.module';
import { TipoMonedaComponent } from 'app/entities/tipo-moneda/tipo-moneda.component';
import { TipoMonedaService } from 'app/entities/tipo-moneda/tipo-moneda.service';
import { TipoMoneda } from 'app/shared/model/tipo-moneda.model';

describe('Component Tests', () => {
  describe('TipoMoneda Management Component', () => {
    let comp: TipoMonedaComponent;
    let fixture: ComponentFixture<TipoMonedaComponent>;
    let service: TipoMonedaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [TipoMonedaComponent]
      })
        .overrideTemplate(TipoMonedaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipoMonedaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoMonedaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TipoMoneda(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.tipoMonedas && comp.tipoMonedas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
