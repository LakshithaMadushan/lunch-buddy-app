import { TestBed } from '@angular/core/testing';

import { AddMembersService } from './add-members.service';

describe('AddMembersService', () => {
  let service: AddMembersService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AddMembersService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
