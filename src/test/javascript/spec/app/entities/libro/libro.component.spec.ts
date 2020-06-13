import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BackendTestModule } from '../../../test.module';
import { LibroComponent } from 'app/entities/libro/libro.component';
import { LibroService } from 'app/entities/libro/libro.service';
import { Libro } from 'app/shared/model/libro.model';

describe('Component Tests', () => {
  describe('Libro Management Component', () => {
    let comp: LibroComponent;
    let fixture: ComponentFixture<LibroComponent>;
    let service: LibroService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [LibroComponent]
      })
        .overrideTemplate(LibroComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LibroComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LibroService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Libro(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.libros && comp.libros[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
