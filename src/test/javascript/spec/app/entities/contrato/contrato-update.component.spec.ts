import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BackendTestModule } from '../../../test.module';
import { ContratoUpdateComponent } from 'app/entities/contrato/contrato-update.component';
import { ContratoService } from 'app/entities/contrato/contrato.service';
import { Contrato } from 'app/shared/model/contrato.model';

describe('Component Tests', () => {
  describe('Contrato Management Update Component', () => {
    let comp: ContratoUpdateComponent;
    let fixture: ComponentFixture<ContratoUpdateComponent>;
    let service: ContratoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [ContratoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ContratoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ContratoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContratoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Contrato(123);
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
        const entity = new Contrato();
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
