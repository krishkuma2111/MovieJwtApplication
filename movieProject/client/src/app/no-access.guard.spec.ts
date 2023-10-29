import { TestBed } from '@angular/core/testing';

import { NoAccessGuard } from './no-access.guard';

describe('NoAccessGuard', () => {
  let guard: NoAccessGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(NoAccessGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
