import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BackendTestModule } from '../../../test.module';
import { UsuarioLibroUpdateComponent } from 'app/entities/usuario-libro/usuario-libro-update.component';
import { UsuarioLibroService } from 'app/entities/usuario-libro/usuario-libro.service';
import { UsuarioLibro } from 'app/shared/model/usuario-libro.model';

describe('Component Tests', () => {
  describe('UsuarioLibro Management Update Component', () => {
    let comp: UsuarioLibroUpdateComponent;
    let fixture: ComponentFixture<UsuarioLibroUpdateComponent>;
    let service: UsuarioLibroService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [UsuarioLibroUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(UsuarioLibroUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UsuarioLibroUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UsuarioLibroService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UsuarioLibro(123);
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
        const entity = new UsuarioLibro();
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
