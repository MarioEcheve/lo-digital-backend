import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BackendTestModule } from '../../../test.module';
import { ArchivoUpdateComponent } from 'app/entities/archivo/archivo-update.component';
import { ArchivoService } from 'app/entities/archivo/archivo.service';
import { Archivo } from 'app/shared/model/archivo.model';

describe('Component Tests', () => {
  describe('Archivo Management Update Component', () => {
    let comp: ArchivoUpdateComponent;
    let fixture: ComponentFixture<ArchivoUpdateComponent>;
    let service: ArchivoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [ArchivoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ArchivoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ArchivoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ArchivoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Archivo(123);
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
        const entity = new Archivo();
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
