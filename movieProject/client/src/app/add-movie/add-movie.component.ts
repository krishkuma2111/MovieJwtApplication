import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Movie } from '../movie';
import { Actor } from '../actor';
import { MovieService } from '../movie.service';
import { DeactivableComponent } from '../deactivable-component';
import { Observable } from 'rxjs';
import { of } from 'rxjs';

@Component({
  selector: 'app-add-movie',
  templateUrl: './add-movie.component.html',
  styleUrls: ['./add-movie.component.css']
})
export class AddMovieComponent implements OnInit, DeactivableComponent {
  movie: Movie=new Movie(0, '', []);
  actors: Actor[]=[];

  actorNames: string[]=['', '', ''];
  movieName: string='';
  isMore: boolean=false;
  isSaved: boolean=false;

  constructor(public router: Router,
              public service: MovieService
    ) { }

  ngOnInit(): void {
  }

  home()
  {
    this.router.navigate([`/movies/home`]);
  }

  list()
  {
    this.router.navigate([`/movies/list`]);
  }

  add()
  {
    this.movie.name=this.movieName;   
    for(let i in this.actorNames)
    {
      if(this.actorNames[i])
      {
        this.actors[i]=new Actor(0, this.actorNames[i]);
      }
    }   
    this.movie.actors=this.actors;

    this.service.addMovie(this.movie).subscribe(()=>{ this.isSaved=true});

  }

  canDeactivate(): Observable<boolean> {
    if (!this.isSaved) {
      const result = window.confirm('There are unsaved changes! Are   you sure?');
      return of(result);
    }
    return of(true);
  }
}
