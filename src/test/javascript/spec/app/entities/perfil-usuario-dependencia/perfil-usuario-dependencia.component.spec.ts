import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BackendTestModule } from '../../../test.module';
import { PerfilUsuarioDependenciaComponent } from 'app/entities/perfil-usuario-dependencia/perfil-usuario-dependencia.component';
import { PerfilUsuarioDependenciaService } from 'app/entities/perfil-usuario-dependencia/perfil-usuario-dependencia.service';
import { PerfilUsuarioDependencia } from 'app/shared/model/perfil-usuario-dependencia.model';

describe('Component Tests', () => {
  describe('PerfilUsuarioDependencia Management Component', () => {
    let comp: PerfilUsuarioDependenciaComponent;
    let fixture: ComponentFixture<PerfilUsuarioDependenciaComponent>;
    let service: PerfilUsuarioDependenciaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [PerfilUsuarioDependenciaComponent]
      })
        .overrideTemplate(PerfilUsuarioDependenciaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PerfilUsuarioDependenciaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PerfilUsuarioDependenciaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PerfilUsuarioDependencia(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.perfilUsuarioDependencias && comp.perfilUsuarioDependencias[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
