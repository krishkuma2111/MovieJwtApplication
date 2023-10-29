import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { LoginStoreService } from './login-store.service';

@Injectable({
  providedIn: 'root'
})
export class NoAccessGuard implements CanActivate {

  constructor(private loginStore: LoginStoreService)
  {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if(this.loginStore.isLoggedIn)
    {
      if(this.loginStore.roles[0]==="ROLE_USER")
      {
        window.confirm("You do not have access to this page");
        return false;
      }
    }  
    return true;
  }
  
}
