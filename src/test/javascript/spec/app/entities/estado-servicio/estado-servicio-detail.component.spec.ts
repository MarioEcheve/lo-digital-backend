import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BackendTestModule } from '../../../test.module';
import { EstadoServicioDetailComponent } from 'app/entities/estado-servicio/estado-servicio-detail.component';
import { EstadoServicio } from 'app/shared/model/estado-servicio.model';

describe('Component Tests', () => {
  describe('EstadoServicio Management Detail Component', () => {
    let comp: EstadoServicioDetailComponent;
    let fixture: ComponentFixture<EstadoServicioDetailComponent>;
    const route = ({ data: of({ estadoServicio: new EstadoServicio(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [EstadoServicioDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EstadoServicioDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EstadoServicioDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load estadoServicio on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.estadoServicio).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
