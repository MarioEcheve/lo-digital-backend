import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BackendTestModule } from '../../../test.module';
import { TipoMonedaDetailComponent } from 'app/entities/tipo-moneda/tipo-moneda-detail.component';
import { TipoMoneda } from 'app/shared/model/tipo-moneda.model';

describe('Component Tests', () => {
  describe('TipoMoneda Management Detail Component', () => {
    let comp: TipoMonedaDetailComponent;
    let fixture: ComponentFixture<TipoMonedaDetailComponent>;
    const route = ({ data: of({ tipoMoneda: new TipoMoneda(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [TipoMonedaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TipoMonedaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TipoMonedaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tipoMoneda on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tipoMoneda).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
