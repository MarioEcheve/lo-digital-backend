import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BackendTestModule } from '../../../test.module';
import { GesNotaUpdateComponent } from 'app/entities/ges-nota/ges-nota-update.component';
import { GesNotaService } from 'app/entities/ges-nota/ges-nota.service';
import { GesNota } from 'app/shared/model/ges-nota.model';

describe('Component Tests', () => {
  describe('GesNota Management Update Component', () => {
    let comp: GesNotaUpdateComponent;
    let fixture: ComponentFixture<GesNotaUpdateComponent>;
    let service: GesNotaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [GesNotaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(GesNotaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GesNotaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GesNotaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new GesNota(123);
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
        const entity = new GesNota();
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
