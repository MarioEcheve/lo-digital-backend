import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BackendTestModule } from '../../../test.module';
import { GesFavoritoComponent } from 'app/entities/ges-favorito/ges-favorito.component';
import { GesFavoritoService } from 'app/entities/ges-favorito/ges-favorito.service';
import { GesFavorito } from 'app/shared/model/ges-favorito.model';

describe('Component Tests', () => {
  describe('GesFavorito Management Component', () => {
    let comp: GesFavoritoComponent;
    let fixture: ComponentFixture<GesFavoritoComponent>;
    let service: GesFavoritoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [GesFavoritoComponent]
      })
        .overrideTemplate(GesFavoritoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GesFavoritoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GesFavoritoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new GesFavorito(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.gesFavoritos && comp.gesFavoritos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
