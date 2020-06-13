import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BackendTestModule } from '../../../test.module';
import { ActividadRubroUpdateComponent } from 'app/entities/actividad-rubro/actividad-rubro-update.component';
import { ActividadRubroService } from 'app/entities/actividad-rubro/actividad-rubro.service';
import { ActividadRubro } from 'app/shared/model/actividad-rubro.model';

describe('Component Tests', () => {
  describe('ActividadRubro Management Update Component', () => {
    let comp: ActividadRubroUpdateComponent;
    let fixture: ComponentFixture<ActividadRubroUpdateComponent>;
    let service: ActividadRubroService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [ActividadRubroUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ActividadRubroUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ActividadRubroUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ActividadRubroService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ActividadRubro(123);
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
        const entity = new ActividadRubro();
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
