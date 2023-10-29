import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MovieListComponent } from './movie-list/movie-list.component';
import { MovieService } from './movie.service';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { ModMovieComponent } from './mod-movie/mod-movie.component';
import { HomeComponent } from './home/home.component';
import { AddMovieComponent } from './add-movie/add-movie.component';
import { RegisterComponent } from './register/register.component';
import { LandingComponent } from './landing/landing.component';
import { LoginComponent } from './login/login.component';
import { HttpMovieInterceptor } from './http-movie.interceptor';
import { LoginService } from './login.service';

@NgModule({
  declarations: [
    AppComponent,
    MovieListComponent,
    ModMovieComponent,
    HomeComponent,
    AddMovieComponent,
    RegisterComponent,
    LandingComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [MovieService, 
              LoginService,
              { provide: HTTP_INTERCEPTORS, useClass: HttpMovieInterceptor, multi: true }
  ],

  bootstrap: [AppComponent]
})
export class AppModule { }
