import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BackendTestModule } from '../../../test.module';
import { UsuarioLibroPerfilDetailComponent } from 'app/entities/usuario-libro-perfil/usuario-libro-perfil-detail.component';
import { UsuarioLibroPerfil } from 'app/shared/model/usuario-libro-perfil.model';

describe('Component Tests', () => {
  describe('UsuarioLibroPerfil Management Detail Component', () => {
    let comp: UsuarioLibroPerfilDetailComponent;
    let fixture: ComponentFixture<UsuarioLibroPerfilDetailComponent>;
    const route = ({ data: of({ usuarioLibroPerfil: new UsuarioLibroPerfil(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [UsuarioLibroPerfilDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(UsuarioLibroPerfilDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UsuarioLibroPerfilDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load usuarioLibroPerfil on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.usuarioLibroPerfil).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
