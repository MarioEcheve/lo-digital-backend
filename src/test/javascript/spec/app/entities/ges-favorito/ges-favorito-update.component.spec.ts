import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BackendTestModule } from '../../../test.module';
import { GesFavoritoUpdateComponent } from 'app/entities/ges-favorito/ges-favorito-update.component';
import { GesFavoritoService } from 'app/entities/ges-favorito/ges-favorito.service';
import { GesFavorito } from 'app/shared/model/ges-favorito.model';

describe('Component Tests', () => {
  describe('GesFavorito Management Update Component', () => {
    let comp: GesFavoritoUpdateComponent;
    let fixture: ComponentFixture<GesFavoritoUpdateComponent>;
    let service: GesFavoritoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [GesFavoritoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(GesFavoritoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GesFavoritoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GesFavoritoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new GesFavorito(123);
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
        const entity = new GesFavorito();
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
