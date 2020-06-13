import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BackendTestModule } from '../../../test.module';
import { GesFavoritoDetailComponent } from 'app/entities/ges-favorito/ges-favorito-detail.component';
import { GesFavorito } from 'app/shared/model/ges-favorito.model';

describe('Component Tests', () => {
  describe('GesFavorito Management Detail Component', () => {
    let comp: GesFavoritoDetailComponent;
    let fixture: ComponentFixture<GesFavoritoDetailComponent>;
    const route = ({ data: of({ gesFavorito: new GesFavorito(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [GesFavoritoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(GesFavoritoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GesFavoritoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load gesFavorito on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.gesFavorito).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
