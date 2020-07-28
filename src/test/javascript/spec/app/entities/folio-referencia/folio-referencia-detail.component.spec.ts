import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BackendTestModule } from '../../../test.module';
import { FolioReferenciaDetailComponent } from 'app/entities/folio-referencia/folio-referencia-detail.component';
import { FolioReferencia } from 'app/shared/model/folio-referencia.model';

describe('Component Tests', () => {
  describe('FolioReferencia Management Detail Component', () => {
    let comp: FolioReferenciaDetailComponent;
    let fixture: ComponentFixture<FolioReferenciaDetailComponent>;
    const route = ({ data: of({ folioReferencia: new FolioReferencia(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [FolioReferenciaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FolioReferenciaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FolioReferenciaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load folioReferencia on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.folioReferencia).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
