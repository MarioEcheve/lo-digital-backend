import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BackendTestModule } from '../../../test.module';
import { UsuarioDependenciaComponent } from 'app/entities/usuario-dependencia/usuario-dependencia.component';
import { UsuarioDependenciaService } from 'app/entities/usuario-dependencia/usuario-dependencia.service';
import { UsuarioDependencia } from 'app/shared/model/usuario-dependencia.model';

describe('Component Tests', () => {
  describe('UsuarioDependencia Management Component', () => {
    let comp: UsuarioDependenciaComponent;
    let fixture: ComponentFixture<UsuarioDependenciaComponent>;
    let service: UsuarioDependenciaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [UsuarioDependenciaComponent]
      })
        .overrideTemplate(UsuarioDependenciaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UsuarioDependenciaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UsuarioDependenciaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new UsuarioDependencia(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.usuarioDependencias && comp.usuarioDependencias[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
