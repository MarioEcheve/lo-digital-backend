import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BackendTestModule } from '../../../test.module';
import { EstadoServicioComponent } from 'app/entities/estado-servicio/estado-servicio.component';
import { EstadoServicioService } from 'app/entities/estado-servicio/estado-servicio.service';
import { EstadoServicio } from 'app/shared/model/estado-servicio.model';

describe('Component Tests', () => {
  describe('EstadoServicio Management Component', () => {
    let comp: EstadoServicioComponent;
    let fixture: ComponentFixture<EstadoServicioComponent>;
    let service: EstadoServicioService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [EstadoServicioComponent]
      })
        .overrideTemplate(EstadoServicioComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EstadoServicioComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EstadoServicioService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new EstadoServicio(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.estadoServicios && comp.estadoServicios[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
