import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BackendTestModule } from '../../../test.module';
import { TipoUsuarioDependenciaUpdateComponent } from 'app/entities/tipo-usuario-dependencia/tipo-usuario-dependencia-update.component';
import { TipoUsuarioDependenciaService } from 'app/entities/tipo-usuario-dependencia/tipo-usuario-dependencia.service';
import { TipoUsuarioDependencia } from 'app/shared/model/tipo-usuario-dependencia.model';

describe('Component Tests', () => {
  describe('TipoUsuarioDependencia Management Update Component', () => {
    let comp: TipoUsuarioDependenciaUpdateComponent;
    let fixture: ComponentFixture<TipoUsuarioDependenciaUpdateComponent>;
    let service: TipoUsuarioDependenciaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [TipoUsuarioDependenciaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TipoUsuarioDependenciaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipoUsuarioDependenciaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoUsuarioDependenciaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TipoUsuarioDependencia(123);
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
        const entity = new TipoUsuarioDependencia();
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
