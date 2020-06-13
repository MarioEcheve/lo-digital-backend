import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BackendTestModule } from '../../../test.module';
import { EstadoLibroUpdateComponent } from 'app/entities/estado-libro/estado-libro-update.component';
import { EstadoLibroService } from 'app/entities/estado-libro/estado-libro.service';
import { EstadoLibro } from 'app/shared/model/estado-libro.model';

describe('Component Tests', () => {
  describe('EstadoLibro Management Update Component', () => {
    let comp: EstadoLibroUpdateComponent;
    let fixture: ComponentFixture<EstadoLibroUpdateComponent>;
    let service: EstadoLibroService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [EstadoLibroUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EstadoLibroUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EstadoLibroUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EstadoLibroService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EstadoLibro(123);
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
        const entity = new EstadoLibro();
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
