import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BackendTestModule } from '../../../test.module';
import { UsuarioLibroDetailComponent } from 'app/entities/usuario-libro/usuario-libro-detail.component';
import { UsuarioLibro } from 'app/shared/model/usuario-libro.model';

describe('Component Tests', () => {
  describe('UsuarioLibro Management Detail Component', () => {
    let comp: UsuarioLibroDetailComponent;
    let fixture: ComponentFixture<UsuarioLibroDetailComponent>;
    const route = ({ data: of({ usuarioLibro: new UsuarioLibro(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [UsuarioLibroDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(UsuarioLibroDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UsuarioLibroDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load usuarioLibro on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.usuarioLibro).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
