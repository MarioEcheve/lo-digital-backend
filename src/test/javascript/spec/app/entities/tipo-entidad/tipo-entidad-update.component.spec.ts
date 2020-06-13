import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BackendTestModule } from '../../../test.module';
import { TipoEntidadUpdateComponent } from 'app/entities/tipo-entidad/tipo-entidad-update.component';
import { TipoEntidadService } from 'app/entities/tipo-entidad/tipo-entidad.service';
import { TipoEntidad } from 'app/shared/model/tipo-entidad.model';

describe('Component Tests', () => {
  describe('TipoEntidad Management Update Component', () => {
    let comp: TipoEntidadUpdateComponent;
    let fixture: ComponentFixture<TipoEntidadUpdateComponent>;
    let service: TipoEntidadService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [TipoEntidadUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TipoEntidadUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipoEntidadUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoEntidadService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TipoEntidad(123);
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
        const entity = new TipoEntidad();
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
