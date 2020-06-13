import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BackendTestModule } from '../../../test.module';
import { PerfilUsuarioDependenciaUpdateComponent } from 'app/entities/perfil-usuario-dependencia/perfil-usuario-dependencia-update.component';
import { PerfilUsuarioDependenciaService } from 'app/entities/perfil-usuario-dependencia/perfil-usuario-dependencia.service';
import { PerfilUsuarioDependencia } from 'app/shared/model/perfil-usuario-dependencia.model';

describe('Component Tests', () => {
  describe('PerfilUsuarioDependencia Management Update Component', () => {
    let comp: PerfilUsuarioDependenciaUpdateComponent;
    let fixture: ComponentFixture<PerfilUsuarioDependenciaUpdateComponent>;
    let service: PerfilUsuarioDependenciaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [PerfilUsuarioDependenciaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PerfilUsuarioDependenciaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PerfilUsuarioDependenciaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PerfilUsuarioDependenciaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PerfilUsuarioDependencia(123);
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
        const entity = new PerfilUsuarioDependencia();
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
