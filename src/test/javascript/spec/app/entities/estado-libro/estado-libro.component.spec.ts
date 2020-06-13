import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BackendTestModule } from '../../../test.module';
import { EstadoLibroComponent } from 'app/entities/estado-libro/estado-libro.component';
import { EstadoLibroService } from 'app/entities/estado-libro/estado-libro.service';
import { EstadoLibro } from 'app/shared/model/estado-libro.model';

describe('Component Tests', () => {
  describe('EstadoLibro Management Component', () => {
    let comp: EstadoLibroComponent;
    let fixture: ComponentFixture<EstadoLibroComponent>;
    let service: EstadoLibroService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [EstadoLibroComponent]
      })
        .overrideTemplate(EstadoLibroComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EstadoLibroComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EstadoLibroService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new EstadoLibro(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.estadoLibros && comp.estadoLibros[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
