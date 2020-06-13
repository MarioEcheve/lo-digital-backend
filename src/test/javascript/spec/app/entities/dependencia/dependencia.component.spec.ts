import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BackendTestModule } from '../../../test.module';
import { DependenciaComponent } from 'app/entities/dependencia/dependencia.component';
import { DependenciaService } from 'app/entities/dependencia/dependencia.service';
import { Dependencia } from 'app/shared/model/dependencia.model';

describe('Component Tests', () => {
  describe('Dependencia Management Component', () => {
    let comp: DependenciaComponent;
    let fixture: ComponentFixture<DependenciaComponent>;
    let service: DependenciaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [DependenciaComponent]
      })
        .overrideTemplate(DependenciaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DependenciaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DependenciaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Dependencia(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.dependencias && comp.dependencias[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
