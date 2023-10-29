import { Injectable } from '@angular/core';
import { AuthResponse } from './auth-response';

@Injectable({
  providedIn: 'root'
})
export class LoginStoreService {
  private _authResponse: AuthResponse | null;  

  constructor() { }

  set authResponse(authResponse: AuthResponse) {
    this._authResponse = authResponse;
  }

  get authResponse() {
    return this._authResponse;
  }

  get token() {
    return this._authResponse.accessToken;
  }

  get roles()
  {
    return this._authResponse.roles;
  }

  isLoggedIn() {
    return this._authResponse != null;
  }

  hasRole() {
    return this._authResponse.roles != null;
  }

  hasAuthResponse() {
    return this.authResponse != null;
  }

  nullifyAuthResponse() {
    this._authResponse = null;
  }
}
