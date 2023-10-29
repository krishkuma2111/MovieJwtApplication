import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModMovieComponent } from './mod-movie.component';

describe('ModMovieComponent', () => {
  let component: ModMovieComponent;
  let fixture: ComponentFixture<ModMovieComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ModMovieComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ModMovieComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
