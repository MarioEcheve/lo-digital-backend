import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BackendTestModule } from '../../../test.module';
import { TipoFolioDetailComponent } from 'app/entities/tipo-folio/tipo-folio-detail.component';
import { TipoFolio } from 'app/shared/model/tipo-folio.model';

describe('Component Tests', () => {
  describe('TipoFolio Management Detail Component', () => {
    let comp: TipoFolioDetailComponent;
    let fixture: ComponentFixture<TipoFolioDetailComponent>;
    const route = ({ data: of({ tipoFolio: new TipoFolio(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [TipoFolioDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TipoFolioDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TipoFolioDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tipoFolio on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tipoFolio).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
