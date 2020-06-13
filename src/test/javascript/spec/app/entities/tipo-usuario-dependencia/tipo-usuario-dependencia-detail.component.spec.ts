import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BackendTestModule } from '../../../test.module';
import { TipoUsuarioDependenciaDetailComponent } from 'app/entities/tipo-usuario-dependencia/tipo-usuario-dependencia-detail.component';
import { TipoUsuarioDependencia } from 'app/shared/model/tipo-usuario-dependencia.model';

describe('Component Tests', () => {
  describe('TipoUsuarioDependencia Management Detail Component', () => {
    let comp: TipoUsuarioDependenciaDetailComponent;
    let fixture: ComponentFixture<TipoUsuarioDependenciaDetailComponent>;
    const route = ({ data: of({ tipoUsuarioDependencia: new TipoUsuarioDependencia(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [TipoUsuarioDependenciaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TipoUsuarioDependenciaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TipoUsuarioDependenciaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tipoUsuarioDependencia on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tipoUsuarioDependencia).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
