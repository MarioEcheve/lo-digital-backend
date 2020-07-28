import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BackendTestModule } from '../../../test.module';
import { FolioReferenciaComponent } from 'app/entities/folio-referencia/folio-referencia.component';
import { FolioReferenciaService } from 'app/entities/folio-referencia/folio-referencia.service';
import { FolioReferencia } from 'app/shared/model/folio-referencia.model';

describe('Component Tests', () => {
  describe('FolioReferencia Management Component', () => {
    let comp: FolioReferenciaComponent;
    let fixture: ComponentFixture<FolioReferenciaComponent>;
    let service: FolioReferenciaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [FolioReferenciaComponent]
      })
        .overrideTemplate(FolioReferenciaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FolioReferenciaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FolioReferenciaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new FolioReferencia(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.folioReferencias && comp.folioReferencias[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
