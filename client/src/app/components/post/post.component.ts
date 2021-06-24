import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Post } from 'src/app/class/post';
import { Commentaire } from 'src/app/class/commentaire';
import { UserService } from 'src/app/services/user.service';
import { PostService } from 'src/app/services/post.service';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})
export class PostComponent implements OnInit {

  id!: Number;
  post: Post = new Post();
  comment: Commentaire = new Commentaire();
  liked!: boolean;

  constructor(private userService: UserService,
              private postService: PostService, 
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(paramMap => {
      var a = paramMap.get('id');
      if (a != null){
        this.id = parseInt(a);
      }
    });
    this.postService.getPostById(this.id).subscribe(
      data => {
        this.post = data;
      },
      error => {
        console.log(error);
      }
    );
  }

  onSubmit(){
    this.comment.id = this.id;
    this.postService.submitComment(this.comment).subscribe(
      _data => {
        this.userService.reloadPage();
      },
      _error => {
        return this.userService.showSnackbar("Unknown error occured", 'Dismiss', 7000);
      }
    );
  }

  likePost() {
    console.log("clicked");
    if (this.post.liked){
      console.log("disliked clicked");
      this.post.liked = false;
      this.postService.dislikePost(this.id).subscribe(data =>{
        console.log(JSON.stringify(data));
      },
      error => {
        console.log(JSON.stringify(error));
      });
    }
    else {
      console.log("like clicked");
      this.post.liked=true;
      this.postService.likePost(this.id).subscribe(data =>{
        console.log(JSON.stringify(data));
      },
      error => {
        console.log(JSON.stringify(error));
      });
    }  
  }
}
