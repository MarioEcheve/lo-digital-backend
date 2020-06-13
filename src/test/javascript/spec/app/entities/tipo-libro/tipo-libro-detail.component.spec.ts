import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BackendTestModule } from '../../../test.module';
import { TipoLibroDetailComponent } from 'app/entities/tipo-libro/tipo-libro-detail.component';
import { TipoLibro } from 'app/shared/model/tipo-libro.model';

describe('Component Tests', () => {
  describe('TipoLibro Management Detail Component', () => {
    let comp: TipoLibroDetailComponent;
    let fixture: ComponentFixture<TipoLibroDetailComponent>;
    const route = ({ data: of({ tipoLibro: new TipoLibro(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [TipoLibroDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TipoLibroDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TipoLibroDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tipoLibro on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tipoLibro).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
