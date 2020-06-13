import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BackendTestModule } from '../../../test.module';
import { TipoMontoDetailComponent } from 'app/entities/tipo-monto/tipo-monto-detail.component';
import { TipoMonto } from 'app/shared/model/tipo-monto.model';

describe('Component Tests', () => {
  describe('TipoMonto Management Detail Component', () => {
    let comp: TipoMontoDetailComponent;
    let fixture: ComponentFixture<TipoMontoDetailComponent>;
    const route = ({ data: of({ tipoMonto: new TipoMonto(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [TipoMontoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TipoMontoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TipoMontoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tipoMonto on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tipoMonto).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
