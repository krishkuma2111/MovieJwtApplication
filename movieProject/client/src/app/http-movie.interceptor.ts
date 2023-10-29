import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { LoginStoreService } from './login-store.service';
import { AuthResponse } from './auth-response';

@Injectable()
export class HttpMovieInterceptor implements HttpInterceptor {

    constructor(private loginStore: LoginStoreService) {
    console.log("in HttpMovieInterceptor")
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> | null {
    console.log("in intercept(): "+this.loginStore.hasAuthResponse())
    
      if(this.loginStore.hasAuthResponse()) { 
        let authResponse = this.loginStore.authResponse;
          console.log("In interceptor: "+authResponse.accessToken);
          const clonedReq = request.clone({
          setHeaders: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + authResponse.accessToken
          }});
        console.log('Token added to HTTP request');
        return next.handle(clonedReq);
      }
      else {
      //No token; proceed request without bearer token
        console.log('No token added to HTTP request');
        return next.handle(request);
      }
  }
 
}