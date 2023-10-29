import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-landing',
  templateUrl: './landing.component.html',
  styleUrls: ['./landing.component.css']
})
export class LandingComponent implements OnInit {
  message: string='';
  constructor(private router: Router,
              private activatedRoute: ActivatedRoute
              ) { }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe((parameters)=>
    {
        let tmp=parameters['msg'];
        tmp? this.message: '';
    });
  }

  register()
  {
    this.router.navigate(['/movies/register']);
  }
  login()
  {
    this.router.navigate(['/movies/login']);
  }
}
