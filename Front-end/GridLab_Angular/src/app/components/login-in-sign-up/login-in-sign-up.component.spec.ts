import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginInSignUpComponent } from './login-in-sign-up.component';

describe('LoginInSignUpComponent', () => {
  let component: LoginInSignUpComponent;
  let fixture: ComponentFixture<LoginInSignUpComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LoginInSignUpComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LoginInSignUpComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
