import { Component, OnInit } from '@angular/core';
import { Login } from '../login';
import { AuthResponse } from '../auth-response';
import { LoginService } from '../login.service';
import { Router } from '@angular/router';
import { LoginStoreService } from '../login-store.service';
import { HttpErrorResponse } from '@angular/common/http';
import { DeactivableComponent } from '../deactivable-component';
import { Observable, of } from 'rxjs';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit, DeactivableComponent {
  login!: Login;
  authResponse!: AuthResponse;
  message: string=null;
  isSaved: boolean=false;

  roleObj: any = [
    {
      name:"ROLE_ADMIN", display: "Admin"
    },
    {
      name:"ROLE_USER", display: "User"
    }    
  ];
  username: string="";
  role: string="";
  password: string="";
  confirmPassword: string="";

  constructor(private loginStore: LoginStoreService,
              private service: LoginService,
              private router: Router) { }

  ngOnInit(): void {
    this.isSaved=false;
  }
  creat()
  {
    this.isSaved=true;

    if(this.password===this.confirmPassword)
    {
      console.log("Role is: "+this.role);
      this.login=new Login(0, this.username, this.password, this.role);
      this.service.register(this.login)
      .subscribe((req: AuthResponse)=>{ this.resolve(req); this.signIn();}
      ,
      (error: HttpErrorResponse)=>{this.reject(error);})      
    }
    else
    {
      this.message="Passwords don't match";
    }
  }

  clearMsg()
  {
    this.message=null;
  }
  
  resolve(response: AuthResponse)
  {
    this.loginStore.authResponse = response;
  }
  
  reject(err: HttpErrorResponse)
  {
    this.message = "User exists...";
  }

  signIn()
  {
    this.router.navigate(['/movies/login']);
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
