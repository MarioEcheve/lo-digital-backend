import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { BackendTestModule } from '../../../test.module';
import { ArchivoDetailComponent } from 'app/entities/archivo/archivo-detail.component';
import { Archivo } from 'app/shared/model/archivo.model';

describe('Component Tests', () => {
  describe('Archivo Management Detail Component', () => {
    let comp: ArchivoDetailComponent;
    let fixture: ComponentFixture<ArchivoDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ archivo: new Archivo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BackendTestModule],
        declarations: [ArchivoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ArchivoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ArchivoDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load archivo on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.archivo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
