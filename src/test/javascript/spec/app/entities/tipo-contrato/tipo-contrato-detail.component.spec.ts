import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BackendTestModule } from '../../../test.module';
import { TipoContratoDetailComponent } from 'app/entities/tipo-contrato/tipo-contrato-detail.component';
import { TipoContrato } from 'app/shared/model/tipo-contrato.model';

describe('Component Tests', () => {
  describe('TipoContrato Management Detail Component', () => {
    let comp: TipoContratoDetailComponent;
    let fixture: ComponentFixture<TipoContratoDetailComponent>;
    const route = ({ data: of({ tipoContrato: new TipoContrato(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [TipoContratoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TipoContratoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TipoContratoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tipoContrato on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tipoContrato).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
