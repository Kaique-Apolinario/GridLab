import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MergerComponent } from './merger.component';

describe('MergerComponent', () => {
  let component: MergerComponent;
  let fixture: ComponentFixture<MergerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MergerComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MergerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
