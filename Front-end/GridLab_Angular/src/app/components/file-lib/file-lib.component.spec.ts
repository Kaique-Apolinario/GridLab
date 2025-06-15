import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FileLibComponent } from './file-lib.component';

describe('FileLibComponent', () => {
  let component: FileLibComponent;
  let fixture: ComponentFixture<FileLibComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FileLibComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FileLibComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
