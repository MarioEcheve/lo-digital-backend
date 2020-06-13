import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BackendTestModule } from '../../../test.module';
import { EstadoRespuestaUpdateComponent } from 'app/entities/estado-respuesta/estado-respuesta-update.component';
import { EstadoRespuestaService } from 'app/entities/estado-respuesta/estado-respuesta.service';
import { EstadoRespuesta } from 'app/shared/model/estado-respuesta.model';

describe('Component Tests', () => {
  describe('EstadoRespuesta Management Update Component', () => {
    let comp: EstadoRespuestaUpdateComponent;
    let fixture: ComponentFixture<EstadoRespuestaUpdateComponent>;
    let service: EstadoRespuestaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [EstadoRespuestaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EstadoRespuestaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EstadoRespuestaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EstadoRespuestaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EstadoRespuesta(123);
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
        const entity = new EstadoRespuesta();
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
