import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BackendTestModule } from '../../../test.module';
import { EstadoRespuestaDetailComponent } from 'app/entities/estado-respuesta/estado-respuesta-detail.component';
import { EstadoRespuesta } from 'app/shared/model/estado-respuesta.model';

describe('Component Tests', () => {
  describe('EstadoRespuesta Management Detail Component', () => {
    let comp: EstadoRespuestaDetailComponent;
    let fixture: ComponentFixture<EstadoRespuestaDetailComponent>;
    const route = ({ data: of({ estadoRespuesta: new EstadoRespuesta(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [EstadoRespuestaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EstadoRespuestaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EstadoRespuestaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load estadoRespuesta on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.estadoRespuesta).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
