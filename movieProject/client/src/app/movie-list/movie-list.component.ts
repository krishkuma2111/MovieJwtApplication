import { Component, OnInit } from '@angular/core';
import { Movie } from '../movie';
import { MovieService } from '../movie.service';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { Router } from '@angular/router';

@Component({
  selector: 'app-movie-list',
  templateUrl: './movie-list.component.html',
  styleUrls: ['./movie-list.component.css']
})
export class MovieListComponent implements OnInit 
{
  hasMovies: boolean=false;
  public movies:Movie[]=[];

  constructor(public service: MovieService, 
              public activatedRoute: ActivatedRoute,
              public route: Router) 
  { 
    this.ngOnInit();
  }

  ngOnInit(): void 
  {
    console.log(`In ngOnInit()`);
    this.movies=[];
    this.service.getMovies()
    .subscribe((x: Movie[])=>{this.movies=x; this.check();});  
  }

  check()
  {
    this.movies.length>0 ? this.hasMovies=true : this.hasMovies=false;
  }
  update(id: number)
  {
    console.log(`id is ${id}`);
    this.route.navigate([`/movies/update/${id}`]);
    this.ngOnInit();
  }

  delete(id: number)
  {
    console.log(`id is ${id}`);
    this.service.delMovie(id).subscribe(()=>{this.ngOnInit();});
  }

  home()
  {
    this.route.navigate([`/movies/home`]);
  }

  add()
  {
    this.route.navigate([`/movies/insert`]);
  }

}
