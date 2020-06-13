import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BackendTestModule } from '../../../test.module';
import { EntidadUpdateComponent } from 'app/entities/entidad/entidad-update.component';
import { EntidadService } from 'app/entities/entidad/entidad.service';
import { Entidad } from 'app/shared/model/entidad.model';

describe('Component Tests', () => {
  describe('Entidad Management Update Component', () => {
    let comp: EntidadUpdateComponent;
    let fixture: ComponentFixture<EntidadUpdateComponent>;
    let service: EntidadService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [EntidadUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EntidadUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EntidadUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EntidadService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Entidad(123);
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
        const entity = new Entidad();
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
