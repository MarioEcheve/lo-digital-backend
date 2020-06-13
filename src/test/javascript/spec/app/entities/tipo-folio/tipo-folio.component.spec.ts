import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BackendTestModule } from '../../../test.module';
import { TipoFolioComponent } from 'app/entities/tipo-folio/tipo-folio.component';
import { TipoFolioService } from 'app/entities/tipo-folio/tipo-folio.service';
import { TipoFolio } from 'app/shared/model/tipo-folio.model';

describe('Component Tests', () => {
  describe('TipoFolio Management Component', () => {
    let comp: TipoFolioComponent;
    let fixture: ComponentFixture<TipoFolioComponent>;
    let service: TipoFolioService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [TipoFolioComponent]
      })
        .overrideTemplate(TipoFolioComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipoFolioComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoFolioService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TipoFolio(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.tipoFolios && comp.tipoFolios[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
