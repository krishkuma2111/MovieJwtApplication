import { TestBed } from '@angular/core/testing';

import { HttpMovieInterceptor } from './http-movie.interceptor';

describe('HttpMovieInterceptor', () => {
  beforeEach(() => TestBed.configureTestingModule({
    providers: [
      HttpMovieInterceptor
      ]
  }));

  it('should be created', () => {
    const interceptor: HttpMovieInterceptor = TestBed.inject(HttpMovieInterceptor);
    expect(interceptor).toBeTruthy();
  });
});
