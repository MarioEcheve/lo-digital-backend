import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BackendTestModule } from '../../../test.module';
import { EntidadComponent } from 'app/entities/entidad/entidad.component';
import { EntidadService } from 'app/entities/entidad/entidad.service';
import { Entidad } from 'app/shared/model/entidad.model';

describe('Component Tests', () => {
  describe('Entidad Management Component', () => {
    let comp: EntidadComponent;
    let fixture: ComponentFixture<EntidadComponent>;
    let service: EntidadService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [EntidadComponent]
      })
        .overrideTemplate(EntidadComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EntidadComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EntidadService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Entidad(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.entidads && comp.entidads[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
