import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BackendTestModule } from '../../../test.module';
import { GesAlertaDetailComponent } from 'app/entities/ges-alerta/ges-alerta-detail.component';
import { GesAlerta } from 'app/shared/model/ges-alerta.model';

describe('Component Tests', () => {
  describe('GesAlerta Management Detail Component', () => {
    let comp: GesAlertaDetailComponent;
    let fixture: ComponentFixture<GesAlertaDetailComponent>;
    const route = ({ data: of({ gesAlerta: new GesAlerta(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [GesAlertaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(GesAlertaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GesAlertaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load gesAlerta on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.gesAlerta).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
