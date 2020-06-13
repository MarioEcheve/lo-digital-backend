import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BackendTestModule } from '../../../test.module';
import { TipoFirmaComponent } from 'app/entities/tipo-firma/tipo-firma.component';
import { TipoFirmaService } from 'app/entities/tipo-firma/tipo-firma.service';
import { TipoFirma } from 'app/shared/model/tipo-firma.model';

describe('Component Tests', () => {
  describe('TipoFirma Management Component', () => {
    let comp: TipoFirmaComponent;
    let fixture: ComponentFixture<TipoFirmaComponent>;
    let service: TipoFirmaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [TipoFirmaComponent]
      })
        .overrideTemplate(TipoFirmaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipoFirmaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoFirmaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TipoFirma(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.tipoFirmas && comp.tipoFirmas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
