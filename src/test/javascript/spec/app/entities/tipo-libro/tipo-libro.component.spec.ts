import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BackendTestModule } from '../../../test.module';
import { TipoLibroComponent } from 'app/entities/tipo-libro/tipo-libro.component';
import { TipoLibroService } from 'app/entities/tipo-libro/tipo-libro.service';
import { TipoLibro } from 'app/shared/model/tipo-libro.model';

describe('Component Tests', () => {
  describe('TipoLibro Management Component', () => {
    let comp: TipoLibroComponent;
    let fixture: ComponentFixture<TipoLibroComponent>;
    let service: TipoLibroService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [TipoLibroComponent]
      })
        .overrideTemplate(TipoLibroComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipoLibroComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoLibroService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TipoLibro(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.tipoLibros && comp.tipoLibros[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
