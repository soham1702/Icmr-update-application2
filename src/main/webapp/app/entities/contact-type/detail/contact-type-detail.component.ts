import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IContactType } from '../contact-type.model';

@Component({
  selector: 'jhi-contact-type-detail',
  templateUrl: './contact-type-detail.component.html',
})
export class ContactTypeDetailComponent implements OnInit {
  contactType: IContactType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contactType }) => {
      this.contactType = contactType;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
