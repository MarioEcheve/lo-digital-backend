import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BackendTestModule } from '../../../test.module';
import { TipoLibroUpdateComponent } from 'app/entities/tipo-libro/tipo-libro-update.component';
import { TipoLibroService } from 'app/entities/tipo-libro/tipo-libro.service';
import { TipoLibro } from 'app/shared/model/tipo-libro.model';

describe('Component Tests', () => {
  describe('TipoLibro Management Update Component', () => {
    let comp: TipoLibroUpdateComponent;
    let fixture: ComponentFixture<TipoLibroUpdateComponent>;
    let service: TipoLibroService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [TipoLibroUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TipoLibroUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipoLibroUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoLibroService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TipoLibro(123);
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
        const entity = new TipoLibro();
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
