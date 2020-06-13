import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BackendTestModule } from '../../../test.module';
import { GesAlertaUpdateComponent } from 'app/entities/ges-alerta/ges-alerta-update.component';
import { GesAlertaService } from 'app/entities/ges-alerta/ges-alerta.service';
import { GesAlerta } from 'app/shared/model/ges-alerta.model';

describe('Component Tests', () => {
  describe('GesAlerta Management Update Component', () => {
    let comp: GesAlertaUpdateComponent;
    let fixture: ComponentFixture<GesAlertaUpdateComponent>;
    let service: GesAlertaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [GesAlertaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(GesAlertaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GesAlertaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GesAlertaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new GesAlerta(123);
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
        const entity = new GesAlerta();
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
