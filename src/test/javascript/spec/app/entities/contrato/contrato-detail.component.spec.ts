import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BackendTestModule } from '../../../test.module';
import { ContratoDetailComponent } from 'app/entities/contrato/contrato-detail.component';
import { Contrato } from 'app/shared/model/contrato.model';

describe('Component Tests', () => {
  describe('Contrato Management Detail Component', () => {
    let comp: ContratoDetailComponent;
    let fixture: ComponentFixture<ContratoDetailComponent>;
    const route = ({ data: of({ contrato: new Contrato(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [ContratoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ContratoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ContratoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load contrato on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.contrato).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
