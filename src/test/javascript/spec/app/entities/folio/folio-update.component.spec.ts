import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BackendTestModule } from '../../../test.module';
import { FolioUpdateComponent } from 'app/entities/folio/folio-update.component';
import { FolioService } from 'app/entities/folio/folio.service';
import { Folio } from 'app/shared/model/folio.model';

describe('Component Tests', () => {
  describe('Folio Management Update Component', () => {
    let comp: FolioUpdateComponent;
    let fixture: ComponentFixture<FolioUpdateComponent>;
    let service: FolioService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [FolioUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FolioUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FolioUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FolioService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Folio(123);
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
        const entity = new Folio();
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
