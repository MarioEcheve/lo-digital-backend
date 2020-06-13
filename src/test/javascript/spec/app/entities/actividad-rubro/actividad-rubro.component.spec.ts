import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BackendTestModule } from '../../../test.module';
import { ActividadRubroComponent } from 'app/entities/actividad-rubro/actividad-rubro.component';
import { ActividadRubroService } from 'app/entities/actividad-rubro/actividad-rubro.service';
import { ActividadRubro } from 'app/shared/model/actividad-rubro.model';

describe('Component Tests', () => {
  describe('ActividadRubro Management Component', () => {
    let comp: ActividadRubroComponent;
    let fixture: ComponentFixture<ActividadRubroComponent>;
    let service: ActividadRubroService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [ActividadRubroComponent]
      })
        .overrideTemplate(ActividadRubroComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ActividadRubroComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ActividadRubroService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ActividadRubro(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.actividadRubros && comp.actividadRubros[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
