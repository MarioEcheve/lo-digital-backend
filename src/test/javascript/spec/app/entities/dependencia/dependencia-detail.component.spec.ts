import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BackendTestModule } from '../../../test.module';
import { DependenciaDetailComponent } from 'app/entities/dependencia/dependencia-detail.component';
import { Dependencia } from 'app/shared/model/dependencia.model';

describe('Component Tests', () => {
  describe('Dependencia Management Detail Component', () => {
    let comp: DependenciaDetailComponent;
    let fixture: ComponentFixture<DependenciaDetailComponent>;
    const route = ({ data: of({ dependencia: new Dependencia(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [DependenciaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DependenciaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DependenciaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load dependencia on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dependencia).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
