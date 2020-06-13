import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BackendTestModule } from '../../../test.module';
import { TipoFirmaDetailComponent } from 'app/entities/tipo-firma/tipo-firma-detail.component';
import { TipoFirma } from 'app/shared/model/tipo-firma.model';

describe('Component Tests', () => {
  describe('TipoFirma Management Detail Component', () => {
    let comp: TipoFirmaDetailComponent;
    let fixture: ComponentFixture<TipoFirmaDetailComponent>;
    const route = ({ data: of({ tipoFirma: new TipoFirma(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [TipoFirmaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TipoFirmaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TipoFirmaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tipoFirma on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tipoFirma).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
