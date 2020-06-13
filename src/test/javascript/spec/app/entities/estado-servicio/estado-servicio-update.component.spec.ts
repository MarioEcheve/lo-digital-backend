import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BackendTestModule } from '../../../test.module';
import { EstadoServicioUpdateComponent } from 'app/entities/estado-servicio/estado-servicio-update.component';
import { EstadoServicioService } from 'app/entities/estado-servicio/estado-servicio.service';
import { EstadoServicio } from 'app/shared/model/estado-servicio.model';

describe('Component Tests', () => {
  describe('EstadoServicio Management Update Component', () => {
    let comp: EstadoServicioUpdateComponent;
    let fixture: ComponentFixture<EstadoServicioUpdateComponent>;
    let service: EstadoServicioService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [EstadoServicioUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EstadoServicioUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EstadoServicioUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EstadoServicioService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EstadoServicio(123);
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
        const entity = new EstadoServicio();
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
