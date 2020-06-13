import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BackendTestModule } from '../../../test.module';
import { ActividadRubroDetailComponent } from 'app/entities/actividad-rubro/actividad-rubro-detail.component';
import { ActividadRubro } from 'app/shared/model/actividad-rubro.model';

describe('Component Tests', () => {
  describe('ActividadRubro Management Detail Component', () => {
    let comp: ActividadRubroDetailComponent;
    let fixture: ComponentFixture<ActividadRubroDetailComponent>;
    const route = ({ data: of({ actividadRubro: new ActividadRubro(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [ActividadRubroDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ActividadRubroDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ActividadRubroDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load actividadRubro on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.actividadRubro).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
