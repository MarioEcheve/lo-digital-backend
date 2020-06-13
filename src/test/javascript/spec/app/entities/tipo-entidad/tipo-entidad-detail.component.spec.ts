import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BackendTestModule } from '../../../test.module';
import { TipoEntidadDetailComponent } from 'app/entities/tipo-entidad/tipo-entidad-detail.component';
import { TipoEntidad } from 'app/shared/model/tipo-entidad.model';

describe('Component Tests', () => {
  describe('TipoEntidad Management Detail Component', () => {
    let comp: TipoEntidadDetailComponent;
    let fixture: ComponentFixture<TipoEntidadDetailComponent>;
    const route = ({ data: of({ tipoEntidad: new TipoEntidad(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [TipoEntidadDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TipoEntidadDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TipoEntidadDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tipoEntidad on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tipoEntidad).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
