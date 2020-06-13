import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BackendTestModule } from '../../../test.module';
import { GesNotaComponent } from 'app/entities/ges-nota/ges-nota.component';
import { GesNotaService } from 'app/entities/ges-nota/ges-nota.service';
import { GesNota } from 'app/shared/model/ges-nota.model';

describe('Component Tests', () => {
  describe('GesNota Management Component', () => {
    let comp: GesNotaComponent;
    let fixture: ComponentFixture<GesNotaComponent>;
    let service: GesNotaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [GesNotaComponent]
      })
        .overrideTemplate(GesNotaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GesNotaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GesNotaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new GesNota(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.gesNotas && comp.gesNotas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
