import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Movie } from './movie';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class MovieService {
  private baseUrl: string = 'http://localhost:8081/movies';

  constructor(public httpClient: HttpClient) { 
    console.log(`In movieService ctor`);
  
  }

  getMovie(id: number): Observable<any> {
    console.log("in getMovie() id is: "+id);
    return this.httpClient.get(`${this.baseUrl}/find/${id}`)
      .pipe(catchError(this.handleError));
  }

  getMovies(): Observable<any> 
  {
    console.log("in getMovies()");
    return this.httpClient.get(`${this.baseUrl}/list`)
      .pipe(catchError(this.handleError));    
  }

  addMovie(movie: Movie): Observable<any> {
    console.log("in addMovie()");
    return this.httpClient.post(`${this.baseUrl}/insert`, movie)
      .pipe(catchError(this.handleError));
  }

  modMovie(movie: Movie): Observable<Object> {
    console.log("in modMovie() id is: "+movie.id);
    return this.httpClient.put(`${this.baseUrl}/update`, movie)
      .pipe(catchError(this.handleError));
  }

  delMovie(id: number): Observable<any> {
    console.log("in delMovie() id is: "+id);
    return this.httpClient.delete(`${this.baseUrl}/delete/${id}`)
      .pipe(catchError(this.handleError));
  }

  handleError(error: HttpErrorResponse) {
    return throwError(() => {console.log("In MovieService(), token is "+sessionStorage.getItem('accessToken')); error;});
  } 
}
