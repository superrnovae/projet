import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Follower } from 'src/app/class/follower';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-following',
  templateUrl: './following.component.html',
  styleUrls: ['./following.component.css']
})
export class FollowingComponent implements OnInit {

  private page: number = 0;
  private readonly size: number = 9;
  private readonly sort: string = "date";
  canLoad = false;
  isLoading = false;

  followings!: Follower[];

  constructor(private readonly userService: UserService,
              public dialogRef: MatDialogRef<FollowingComponent>,
              @Inject(MAT_DIALOG_DATA) public username: string) { }

  ngOnInit(): void {
    this.canLoad = true;
    this.getSubscriptions();
  }

  closeDialog() {
    this.dialogRef.close();
  }

  getSubscriptions(){
    this.isLoading = true;
    setTimeout(() => {
      this.userService.getSubscriptions(this.username, this.page, this.size, this.sort).subscribe(data => {
        console.log(data);
        this.followings = data;
        this.isLoading = false;
      },
      error => {
        this.isLoading = false;
        console.log(error);
      })
    }, 1000);
  }

  follow(){

  }

  unfollow(){

  }
  

}