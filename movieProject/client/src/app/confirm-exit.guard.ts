import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanDeactivate, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { DeactivableComponent } from './deactivable-component';

@Injectable({
  providedIn: 'root'
})
export class ConfirmExitGuard implements CanDeactivate<DeactivableComponent> {
  canDeactivate(
    deactivableComponent: DeactivableComponent,
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot,
    nextState?: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
      return window.confirm("Do you want to navigate away from this page");
    return true;
  } 
}

