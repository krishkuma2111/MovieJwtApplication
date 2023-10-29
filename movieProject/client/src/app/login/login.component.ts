import { Component, OnInit } from '@angular/core';
import { Login } from '../login';
import { Router } from '@angular/router';
import { LoginService } from '../login.service';
import { AuthResponse } from '../auth-response';
import { LoginStoreService } from '../login-store.service';
import { HttpErrorResponse } from '@angular/common/http';
import { DeactivableComponent } from '../deactivable-component';
import { Observable, of } from 'rxjs';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, DeactivableComponent {
  authResponse: AuthResponse = null;
  login: Login = null;

  username: string="";
  password: string="";
  message: string="";
  isSaved: boolean=false;
  constructor(private loginStore: LoginStoreService,
              private service: LoginService,
              private router: Router) { }

  ngOnInit(): void {
    this.isSaved=false;
    console.log("In ngOnInit() in LoginComponent");
  }
  signIn()
  {
      this.isSaved=true;
      this.login=new Login(0, this.username, this.password, null);
      this.service.signIn(this.login)
      .subscribe((res: any)=>{this.resolve(res); this.home();
      },
      (error: HttpErrorResponse)=>{this.reject(error);})      
  }    

  
  resolve(response: any)
  {
    console.log("LoginComponent: in resolve() "+JSON.stringify(response));
    this.authResponse=<AuthResponse>response;
    if(this.authResponse){    
      this.loginStore.authResponse = this.authResponse;
      console.log("LoginComponent: authResponse is truthy, stored in LoginStoreService");
    }
    else
    {
      console.log("LoginComponent: authResponse is falsy");
    }
  }

  reject(error: HttpErrorResponse)
  {
    this.message="Invalid username/password";
    //console.log(error);
  }

  home()
  {
    console.log('home()');
    this.router.navigate(['/movies/home']);
  }

  clearMsg()
  {
    this.message=null;
  }

  land()
  {
    this.router.navigate(['/movies/landing']);
  }

  canDeactivate(): Observable<boolean> {
    if (!this.isSaved) {
      const result = window.confirm('There are unsaved changes! Are   you sure?');
      return of(result);
    }
    return of(true);
  }
}

