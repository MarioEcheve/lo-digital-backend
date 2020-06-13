import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BackendTestModule } from '../../../test.module';
import { TipoMontoUpdateComponent } from 'app/entities/tipo-monto/tipo-monto-update.component';
import { TipoMontoService } from 'app/entities/tipo-monto/tipo-monto.service';
import { TipoMonto } from 'app/shared/model/tipo-monto.model';

describe('Component Tests', () => {
  describe('TipoMonto Management Update Component', () => {
    let comp: TipoMontoUpdateComponent;
    let fixture: ComponentFixture<TipoMontoUpdateComponent>;
    let service: TipoMontoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [TipoMontoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TipoMontoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipoMontoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoMontoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TipoMonto(123);
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
        const entity = new TipoMonto();
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
