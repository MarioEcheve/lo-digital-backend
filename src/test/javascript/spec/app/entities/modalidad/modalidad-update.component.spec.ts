import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BackendTestModule } from '../../../test.module';
import { ModalidadUpdateComponent } from 'app/entities/modalidad/modalidad-update.component';
import { ModalidadService } from 'app/entities/modalidad/modalidad.service';
import { Modalidad } from 'app/shared/model/modalidad.model';

describe('Component Tests', () => {
  describe('Modalidad Management Update Component', () => {
    let comp: ModalidadUpdateComponent;
    let fixture: ComponentFixture<ModalidadUpdateComponent>;
    let service: ModalidadService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [ModalidadUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ModalidadUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ModalidadUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ModalidadService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Modalidad(123);
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
        const entity = new Modalidad();
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
