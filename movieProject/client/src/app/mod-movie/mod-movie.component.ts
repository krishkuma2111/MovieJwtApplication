import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MovieService } from '../movie.service';
import { Movie } from '../movie';
import { Actor } from '../actor';
import { Router } from '@angular/router';
import { DeactivableComponent } from '../deactivable-component';
import { Observable, of } from 'rxjs';

@Component({
  selector: 'app-mod-movie',
  templateUrl: './mod-movie.component.html',
  styleUrls: ['./mod-movie.component.css']
})
export class ModMovieComponent implements OnInit, DeactivableComponent 
{
  public movie!: Movie
  public dummy!: Movie;
  public actors: Actor[]=[];
  public movieName: string='';
  public actorIds: number[]=[0, 0, 0];
  public actorNames:string[]=[' ', ' ', ' '];
  isSaved: boolean=false;

  constructor(public route: ActivatedRoute,
              public router: Router,
              public service: MovieService) 
  { 
    this.movie=new Movie(0, '', []);
    this.dummy=new Movie(0, '', []);
    this.ngOnInit();
  }

  ngOnInit(): void 
  {
    this.isSaved=false;
    let id=0;
    this.route.params.subscribe((parameters)=>
    {
        id=parseInt(parameters['id']);
        console.log('In modify, id is '+id);
    });

  
    this.service.getMovie(id).subscribe((x: Movie)=>
    {
      this.movie=x;
      this.movieName=x.name;
      for(let i in this.movie.actors)
      {
        if(this.movie.actors[i].name)
        {
          this.actorIds[i]=this.movie.actors[i].id;
          this.actorNames[i]=this.movie.actors[i].name;
        }
      }
      
    });
  }

  update(): void
  {
    this.isSaved=true;

    for(let i in this.actorNames)
    {
      if(this.actorNames[i])
      {
        this.actors[i]=new Actor(this.actorIds[i], this.actorNames[i]);
      }
    }
    this.dummy=new Movie(this.movie.id, this.movieName, this.actors);
    this.service.modMovie(this.dummy).subscribe(()=>{this.isSaved=true;});
  }

  home()
  {
    this.router.navigate([`/movies/home`]);
  }

  list()
  {
    this.router.navigate([`/movies/list`]);
  }

  canDeactivate(): Observable<boolean> {
    if (!this.isSaved) {
      const result = window.confirm('There are unsaved changes! Are   you sure?');
      return of(result);
    }
    return of(true);
  }
}


