import { TestBed } from '@angular/core/testing';

import { UserSessionSharedService } from './user-session-shared.service';

describe('UserSessionSharedService', () => {
  let service: UserSessionSharedService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UserSessionSharedService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
