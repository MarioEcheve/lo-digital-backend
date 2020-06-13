import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BackendTestModule } from '../../../test.module';
import { ModalidadDetailComponent } from 'app/entities/modalidad/modalidad-detail.component';
import { Modalidad } from 'app/shared/model/modalidad.model';

describe('Component Tests', () => {
  describe('Modalidad Management Detail Component', () => {
    let comp: ModalidadDetailComponent;
    let fixture: ComponentFixture<ModalidadDetailComponent>;
    const route = ({ data: of({ modalidad: new Modalidad(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [ModalidadDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ModalidadDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ModalidadDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load modalidad on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.modalidad).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
