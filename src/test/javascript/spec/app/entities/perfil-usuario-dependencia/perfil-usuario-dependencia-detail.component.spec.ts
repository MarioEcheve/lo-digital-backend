import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BackendTestModule } from '../../../test.module';
import { PerfilUsuarioDependenciaDetailComponent } from 'app/entities/perfil-usuario-dependencia/perfil-usuario-dependencia-detail.component';
import { PerfilUsuarioDependencia } from 'app/shared/model/perfil-usuario-dependencia.model';

describe('Component Tests', () => {
  describe('PerfilUsuarioDependencia Management Detail Component', () => {
    let comp: PerfilUsuarioDependenciaDetailComponent;
    let fixture: ComponentFixture<PerfilUsuarioDependenciaDetailComponent>;
    const route = ({ data: of({ perfilUsuarioDependencia: new PerfilUsuarioDependencia(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [PerfilUsuarioDependenciaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PerfilUsuarioDependenciaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PerfilUsuarioDependenciaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load perfilUsuarioDependencia on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.perfilUsuarioDependencia).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
