import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BackendTestModule } from '../../../test.module';
import { ModalidadComponent } from 'app/entities/modalidad/modalidad.component';
import { ModalidadService } from 'app/entities/modalidad/modalidad.service';
import { Modalidad } from 'app/shared/model/modalidad.model';

describe('Component Tests', () => {
  describe('Modalidad Management Component', () => {
    let comp: ModalidadComponent;
    let fixture: ComponentFixture<ModalidadComponent>;
    let service: ModalidadService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [ModalidadComponent]
      })
        .overrideTemplate(ModalidadComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ModalidadComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ModalidadService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Modalidad(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.modalidads && comp.modalidads[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
