import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BackendTestModule } from '../../../test.module';
import { GesNotaDetailComponent } from 'app/entities/ges-nota/ges-nota-detail.component';
import { GesNota } from 'app/shared/model/ges-nota.model';

describe('Component Tests', () => {
  describe('GesNota Management Detail Component', () => {
    let comp: GesNotaDetailComponent;
    let fixture: ComponentFixture<GesNotaDetailComponent>;
    const route = ({ data: of({ gesNota: new GesNota(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [GesNotaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(GesNotaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GesNotaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load gesNota on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.gesNota).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
