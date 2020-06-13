import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BackendTestModule } from '../../../test.module';
import { ArchivoComponent } from 'app/entities/archivo/archivo.component';
import { ArchivoService } from 'app/entities/archivo/archivo.service';
import { Archivo } from 'app/shared/model/archivo.model';

describe('Component Tests', () => {
  describe('Archivo Management Component', () => {
    let comp: ArchivoComponent;
    let fixture: ComponentFixture<ArchivoComponent>;
    let service: ArchivoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [ArchivoComponent]
      })
        .overrideTemplate(ArchivoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ArchivoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ArchivoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Archivo(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.archivos && comp.archivos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
