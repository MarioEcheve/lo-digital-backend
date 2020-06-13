import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BackendTestModule } from '../../../test.module';
import { TipoFolioUpdateComponent } from 'app/entities/tipo-folio/tipo-folio-update.component';
import { TipoFolioService } from 'app/entities/tipo-folio/tipo-folio.service';
import { TipoFolio } from 'app/shared/model/tipo-folio.model';

describe('Component Tests', () => {
  describe('TipoFolio Management Update Component', () => {
    let comp: TipoFolioUpdateComponent;
    let fixture: ComponentFixture<TipoFolioUpdateComponent>;
    let service: TipoFolioService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [TipoFolioUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TipoFolioUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipoFolioUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoFolioService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TipoFolio(123);
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
        const entity = new TipoFolio();
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
