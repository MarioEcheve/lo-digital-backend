import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BackendTestModule } from '../../../test.module';
import { EstadoLibroDetailComponent } from 'app/entities/estado-libro/estado-libro-detail.component';
import { EstadoLibro } from 'app/shared/model/estado-libro.model';

describe('Component Tests', () => {
  describe('EstadoLibro Management Detail Component', () => {
    let comp: EstadoLibroDetailComponent;
    let fixture: ComponentFixture<EstadoLibroDetailComponent>;
    const route = ({ data: of({ estadoLibro: new EstadoLibro(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [EstadoLibroDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EstadoLibroDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EstadoLibroDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load estadoLibro on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.estadoLibro).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
