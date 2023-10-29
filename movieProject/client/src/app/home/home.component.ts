import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../login.service';
import { HttpErrorResponse } from '@angular/common/http';
import { LoginStoreService } from '../login-store.service';
import { AuthResponse } from '../auth-response';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  message: string='';
  authResponse: AuthResponse=null;
  constructor(private router: Router,
              private service: LoginService,
              private loginStore: LoginStoreService
              ) 
              { 
              }

  ngOnInit(): void {
    console.log("In HomeComponent ngOnInit(): "+JSON.stringify(this.loginStore.authResponse));
  }
  add(): void
  {
    this.router.navigate(['/movies/insert']);
  }
  list(): void
  {
    this.router.navigate(['/movies/list'])
  }
  logout(): void
  {
    console.log("In HomeComponent, logOut(): ");
    
    if(this.loginStore.hasAuthResponse())
    {
      this.authResponse = this.loginStore.authResponse;
      console.log("AuthResponse: "+JSON.stringify(this.loginStore.authResponse));
      this.service.signOut(this.authResponse).subscribe
      (()=>{this.resolve();}
    , (error: HttpErrorResponse)=>{ this.reject(error)});
    }
    else
    {
      console.log("In HomeComponent, authResponse is null: "+this.authResponse);
    }
  }
  login(): void
  {
    this.router.navigate(['/movies/login'])
  }

  resolve() {
    console.log("Signing out...");
    this.loginStore.nullifyAuthResponse();
    this.login()
  }

  reject(error: HttpErrorResponse)
  {
    console.log("In HomeComponent->reject(): during logout");
    error? console.log("error object is truthy") : console.log("error object falsy");
    console.log("error.error: "+error.error.toString());
    console.log("error.message: "+error.message);
    console.log("error.status: "+error.status);
  }
}
