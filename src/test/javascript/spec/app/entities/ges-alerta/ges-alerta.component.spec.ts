import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BackendTestModule } from '../../../test.module';
import { GesAlertaComponent } from 'app/entities/ges-alerta/ges-alerta.component';
import { GesAlertaService } from 'app/entities/ges-alerta/ges-alerta.service';
import { GesAlerta } from 'app/shared/model/ges-alerta.model';

describe('Component Tests', () => {
  describe('GesAlerta Management Component', () => {
    let comp: GesAlertaComponent;
    let fixture: ComponentFixture<GesAlertaComponent>;
    let service: GesAlertaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [GesAlertaComponent]
      })
        .overrideTemplate(GesAlertaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GesAlertaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GesAlertaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new GesAlerta(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.gesAlertas && comp.gesAlertas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
