import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BackendTestModule } from '../../../test.module';
import { TipoEntidadComponent } from 'app/entities/tipo-entidad/tipo-entidad.component';
import { TipoEntidadService } from 'app/entities/tipo-entidad/tipo-entidad.service';
import { TipoEntidad } from 'app/shared/model/tipo-entidad.model';

describe('Component Tests', () => {
  describe('TipoEntidad Management Component', () => {
    let comp: TipoEntidadComponent;
    let fixture: ComponentFixture<TipoEntidadComponent>;
    let service: TipoEntidadService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [TipoEntidadComponent]
      })
        .overrideTemplate(TipoEntidadComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipoEntidadComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoEntidadService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TipoEntidad(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.tipoEntidads && comp.tipoEntidads[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
