import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BackendTestModule } from '../../../test.module';
import { FolioComponent } from 'app/entities/folio/folio.component';
import { FolioService } from 'app/entities/folio/folio.service';
import { Folio } from 'app/shared/model/folio.model';

describe('Component Tests', () => {
  describe('Folio Management Component', () => {
    let comp: FolioComponent;
    let fixture: ComponentFixture<FolioComponent>;
    let service: FolioService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [FolioComponent]
      })
        .overrideTemplate(FolioComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FolioComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FolioService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Folio(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.folios && comp.folios[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
