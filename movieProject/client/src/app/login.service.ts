import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Login } from './login';
import { AuthResponse } from './auth-response';
import { LoginStoreService } from './login-store.service';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private baseUrl: string = 'http://localhost:8081';
  authResponse!: AuthResponse;

  constructor(private loginStore: LoginStoreService,
              private httpClient: HttpClient,
            ) { 
    console.log(`In login service`);
  }

  register(login: Login): any {
    //this.loginStore.nullifyAuthResponse;
    return this.httpClient.post(`${this.baseUrl}/register`, login); 
  }

  signIn(login: Login): any {    
    //this.loginStore.nullifyAuthResponse;
    return this.httpClient.post(`${this.baseUrl}/login`, login);
  }

  signOut(authResponse: AuthResponse): any { 
    console.log("In loginService signOut(): "+JSON.stringify(authResponse));
    return this.httpClient.get(`${this.baseUrl}` + '/signout', { observe: 'response'});
  }
}
