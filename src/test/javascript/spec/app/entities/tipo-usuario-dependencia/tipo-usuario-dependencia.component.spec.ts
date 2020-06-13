import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BackendTestModule } from '../../../test.module';
import { TipoUsuarioDependenciaComponent } from 'app/entities/tipo-usuario-dependencia/tipo-usuario-dependencia.component';
import { TipoUsuarioDependenciaService } from 'app/entities/tipo-usuario-dependencia/tipo-usuario-dependencia.service';
import { TipoUsuarioDependencia } from 'app/shared/model/tipo-usuario-dependencia.model';

describe('Component Tests', () => {
  describe('TipoUsuarioDependencia Management Component', () => {
    let comp: TipoUsuarioDependenciaComponent;
    let fixture: ComponentFixture<TipoUsuarioDependenciaComponent>;
    let service: TipoUsuarioDependenciaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [TipoUsuarioDependenciaComponent]
      })
        .overrideTemplate(TipoUsuarioDependenciaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipoUsuarioDependenciaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoUsuarioDependenciaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TipoUsuarioDependencia(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.tipoUsuarioDependencias && comp.tipoUsuarioDependencias[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
