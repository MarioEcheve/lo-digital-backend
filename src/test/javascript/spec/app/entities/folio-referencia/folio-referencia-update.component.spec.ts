import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BackendTestModule } from '../../../test.module';
import { FolioReferenciaUpdateComponent } from 'app/entities/folio-referencia/folio-referencia-update.component';
import { FolioReferenciaService } from 'app/entities/folio-referencia/folio-referencia.service';
import { FolioReferencia } from 'app/shared/model/folio-referencia.model';

describe('Component Tests', () => {
  describe('FolioReferencia Management Update Component', () => {
    let comp: FolioReferenciaUpdateComponent;
    let fixture: ComponentFixture<FolioReferenciaUpdateComponent>;
    let service: FolioReferenciaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [FolioReferenciaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FolioReferenciaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FolioReferenciaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FolioReferenciaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FolioReferencia(123);
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
        const entity = new FolioReferencia();
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
