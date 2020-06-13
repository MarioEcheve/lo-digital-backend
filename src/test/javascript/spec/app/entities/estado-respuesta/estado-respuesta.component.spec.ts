import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BackendTestModule } from '../../../test.module';
import { EstadoRespuestaComponent } from 'app/entities/estado-respuesta/estado-respuesta.component';
import { EstadoRespuestaService } from 'app/entities/estado-respuesta/estado-respuesta.service';
import { EstadoRespuesta } from 'app/shared/model/estado-respuesta.model';

describe('Component Tests', () => {
  describe('EstadoRespuesta Management Component', () => {
    let comp: EstadoRespuestaComponent;
    let fixture: ComponentFixture<EstadoRespuestaComponent>;
    let service: EstadoRespuestaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [EstadoRespuestaComponent]
      })
        .overrideTemplate(EstadoRespuestaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EstadoRespuestaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EstadoRespuestaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new EstadoRespuesta(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.estadoRespuestas && comp.estadoRespuestas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
