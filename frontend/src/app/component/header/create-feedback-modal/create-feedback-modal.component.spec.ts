import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateFeedbackModalComponent } from './create-feedback-modal.component';

describe('CreateFeedbackModalComponent', () => {
  let component: CreateFeedbackModalComponent;
  let fixture: ComponentFixture<CreateFeedbackModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateFeedbackModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateFeedbackModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
