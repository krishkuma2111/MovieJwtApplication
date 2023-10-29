import { ActivatedRouteSnapshot, CanDeactivate, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';

type DeactivateComponentType = Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree;

export interface DeactivableComponent {
  canDeactivate: () => DeactivateComponentType;
}
