import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BackendTestModule } from '../../../test.module';
import { UsuarioDependenciaUpdateComponent } from 'app/entities/usuario-dependencia/usuario-dependencia-update.component';
import { UsuarioDependenciaService } from 'app/entities/usuario-dependencia/usuario-dependencia.service';
import { UsuarioDependencia } from 'app/shared/model/usuario-dependencia.model';

describe('Component Tests', () => {
  describe('UsuarioDependencia Management Update Component', () => {
    let comp: UsuarioDependenciaUpdateComponent;
    let fixture: ComponentFixture<UsuarioDependenciaUpdateComponent>;
    let service: UsuarioDependenciaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [UsuarioDependenciaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(UsuarioDependenciaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UsuarioDependenciaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UsuarioDependenciaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UsuarioDependencia(123);
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
        const entity = new UsuarioDependencia();
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
