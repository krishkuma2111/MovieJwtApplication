import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MovieListComponent } from './movie-list/movie-list.component';
import { AddMovieComponent } from './add-movie/add-movie.component';
import { ModMovieComponent } from './mod-movie/mod-movie.component';
import { HomeComponent } from './home/home.component';
import { LandingComponent } from './landing/landing.component';
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { AuthGuard } from './auth.guard';
import { NoAccessGuard } from './no-access.guard';
import { ConfirmExitGuard } from './confirm-exit.guard';

//import { SureGuard } from './sure.guard';

const routes: Routes = [
  {
    path: '', pathMatch:"full", redirectTo: 'movies/landing',
  },
  { 
    path: 'movies/landing',
    component: LandingComponent,
  },
  { 
    path: 'movies/register',
    component: RegisterComponent
    //canActivate: [ SureGuard ]
    //canDeactivate: [ ConfirmExitGuard ]
  },
  { 
    path: 'movies/login',
    component: LoginComponent
    //canDeactivate: [ ConfirmExitGuard ]
  },
  { 
    path: 'movies/home',
    component: HomeComponent,
    canActivate: [ AuthGuard ]
  },
  {
    path: 'movies/insert',
    component: AddMovieComponent,
    canActivate: [ AuthGuard, NoAccessGuard ],
    canDeactivate: [ ConfirmExitGuard ]
  },
  { 
    path: 'movies/update/:id',
    component: ModMovieComponent,
    canActivate: [ AuthGuard, NoAccessGuard ],
    canDeactivate: [ ConfirmExitGuard ]
  },
  { 
    path: 'movies/list',
    component: MovieListComponent,
    canActivate: [ AuthGuard ]
  },
  {
    path: "**", redirectTo: 'movies/landing' 
  }  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
