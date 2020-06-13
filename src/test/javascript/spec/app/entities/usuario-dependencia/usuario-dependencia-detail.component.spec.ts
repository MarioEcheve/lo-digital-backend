import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BackendTestModule } from '../../../test.module';
import { UsuarioDependenciaDetailComponent } from 'app/entities/usuario-dependencia/usuario-dependencia-detail.component';
import { UsuarioDependencia } from 'app/shared/model/usuario-dependencia.model';

describe('Component Tests', () => {
  describe('UsuarioDependencia Management Detail Component', () => {
    let comp: UsuarioDependenciaDetailComponent;
    let fixture: ComponentFixture<UsuarioDependenciaDetailComponent>;
    const route = ({ data: of({ usuarioDependencia: new UsuarioDependencia(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [UsuarioDependenciaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(UsuarioDependenciaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UsuarioDependenciaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load usuarioDependencia on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.usuarioDependencia).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
