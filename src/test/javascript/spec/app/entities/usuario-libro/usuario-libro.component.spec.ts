import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BackendTestModule } from '../../../test.module';
import { UsuarioLibroComponent } from 'app/entities/usuario-libro/usuario-libro.component';
import { UsuarioLibroService } from 'app/entities/usuario-libro/usuario-libro.service';
import { UsuarioLibro } from 'app/shared/model/usuario-libro.model';

describe('Component Tests', () => {
  describe('UsuarioLibro Management Component', () => {
    let comp: UsuarioLibroComponent;
    let fixture: ComponentFixture<UsuarioLibroComponent>;
    let service: UsuarioLibroService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [UsuarioLibroComponent]
      })
        .overrideTemplate(UsuarioLibroComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UsuarioLibroComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UsuarioLibroService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new UsuarioLibro(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.usuarioLibros && comp.usuarioLibros[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
