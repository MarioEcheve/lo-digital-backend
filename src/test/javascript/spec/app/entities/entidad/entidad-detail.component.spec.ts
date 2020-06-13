import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BackendTestModule } from '../../../test.module';
import { EntidadDetailComponent } from 'app/entities/entidad/entidad-detail.component';
import { Entidad } from 'app/shared/model/entidad.model';

describe('Component Tests', () => {
  describe('Entidad Management Detail Component', () => {
    let comp: EntidadDetailComponent;
    let fixture: ComponentFixture<EntidadDetailComponent>;
    const route = ({ data: of({ entidad: new Entidad(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [EntidadDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EntidadDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EntidadDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load entidad on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.entidad).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
