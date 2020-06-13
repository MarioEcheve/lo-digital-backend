import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BackendTestModule } from '../../../test.module';
import { UsuarioLibroPerfilUpdateComponent } from 'app/entities/usuario-libro-perfil/usuario-libro-perfil-update.component';
import { UsuarioLibroPerfilService } from 'app/entities/usuario-libro-perfil/usuario-libro-perfil.service';
import { UsuarioLibroPerfil } from 'app/shared/model/usuario-libro-perfil.model';

describe('Component Tests', () => {
  describe('UsuarioLibroPerfil Management Update Component', () => {
    let comp: UsuarioLibroPerfilUpdateComponent;
    let fixture: ComponentFixture<UsuarioLibroPerfilUpdateComponent>;
    let service: UsuarioLibroPerfilService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [UsuarioLibroPerfilUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(UsuarioLibroPerfilUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UsuarioLibroPerfilUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UsuarioLibroPerfilService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UsuarioLibroPerfil(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new UsuarioLibroPerfil();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
