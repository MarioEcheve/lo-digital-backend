import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BackendTestModule } from '../../../test.module';
import { TipoFirmaUpdateComponent } from 'app/entities/tipo-firma/tipo-firma-update.component';
import { TipoFirmaService } from 'app/entities/tipo-firma/tipo-firma.service';
import { TipoFirma } from 'app/shared/model/tipo-firma.model';

describe('Component Tests', () => {
  describe('TipoFirma Management Update Component', () => {
    let comp: TipoFirmaUpdateComponent;
    let fixture: ComponentFixture<TipoFirmaUpdateComponent>;
    let service: TipoFirmaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [TipoFirmaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TipoFirmaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipoFirmaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoFirmaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TipoFirma(123);
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
        const entity = new TipoFirma();
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
