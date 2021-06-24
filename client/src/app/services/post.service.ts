import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Commentaire } from '../class/commentaire';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  private API: String = 'http://localhost:8081/api/post';

  constructor(private http: HttpClient) { }

  public postPhoto(form: FormData): Observable<any>{
    return this.http.post(this.API+ '/create', form);
  }

  public getPostById(id: Number): Observable<any>{
    return this.http.get(`${this.API}/${id}`);
  }

  public submitComment(comment: Commentaire): Observable<any>{
    return this.http.post(this.API + '/addcomment', comment);
  }

  public likePost(id: Number): Observable<any> {
    return this.http.post(this.API + '/like', id);
  }

  public dislikePost(id: Number): Observable<any>{
    return this.http.post(this.API + '/dislike', id);
  }
}

