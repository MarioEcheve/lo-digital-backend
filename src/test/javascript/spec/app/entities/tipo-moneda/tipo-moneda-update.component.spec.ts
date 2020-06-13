import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BackendTestModule } from '../../../test.module';
import { TipoMonedaUpdateComponent } from 'app/entities/tipo-moneda/tipo-moneda-update.component';
import { TipoMonedaService } from 'app/entities/tipo-moneda/tipo-moneda.service';
import { TipoMoneda } from 'app/shared/model/tipo-moneda.model';

describe('Component Tests', () => {
  describe('TipoMoneda Management Update Component', () => {
    let comp: TipoMonedaUpdateComponent;
    let fixture: ComponentFixture<TipoMonedaUpdateComponent>;
    let service: TipoMonedaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [TipoMonedaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TipoMonedaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipoMonedaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoMonedaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TipoMoneda(123);
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
        const entity = new TipoMoneda();
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
