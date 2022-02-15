jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { MunicipalCorpService } from '../service/municipal-corp.service';

import { MunicipalCorpDeleteDialogComponent } from './municipal-corp-delete-dialog.component';

describe('MunicipalCorp Management Delete Component', () => {
  let comp: MunicipalCorpDeleteDialogComponent;
  let fixture: ComponentFixture<MunicipalCorpDeleteDialogComponent>;
  let service: MunicipalCorpService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [MunicipalCorpDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(MunicipalCorpDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(MunicipalCorpDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(MunicipalCorpService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      })
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
