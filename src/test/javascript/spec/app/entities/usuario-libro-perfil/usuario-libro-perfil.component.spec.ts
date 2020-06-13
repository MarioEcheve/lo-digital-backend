import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BackendTestModule } from '../../../test.module';
import { UsuarioLibroPerfilComponent } from 'app/entities/usuario-libro-perfil/usuario-libro-perfil.component';
import { UsuarioLibroPerfilService } from 'app/entities/usuario-libro-perfil/usuario-libro-perfil.service';
import { UsuarioLibroPerfil } from 'app/shared/model/usuario-libro-perfil.model';

describe('Component Tests', () => {
  describe('UsuarioLibroPerfil Management Component', () => {
    let comp: UsuarioLibroPerfilComponent;
    let fixture: ComponentFixture<UsuarioLibroPerfilComponent>;
    let service: UsuarioLibroPerfilService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [UsuarioLibroPerfilComponent]
      })
        .overrideTemplate(UsuarioLibroPerfilComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UsuarioLibroPerfilComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UsuarioLibroPerfilService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new UsuarioLibroPerfil(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.usuarioLibroPerfils && comp.usuarioLibroPerfils[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
