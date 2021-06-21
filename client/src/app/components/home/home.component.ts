import { Component, Inject, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { User } from 'src/app/class/user';
import { UserService } from 'src/app/services/user.service';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';

export interface DialogData {
  animal: string;
  name: string;
}

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  
  cols! : number;
  
  gridByBreakpoint = {
    xl: 3,
    lg: 3,
    md: 2,
    sm: 2,
    xs: 1
  }

  user!: User;

  constructor(private userService: UserService, private route: ActivatedRoute, public dialog: MatDialog, private breakpointObserver: BreakpointObserver) {
    this.breakpointObserver.observe([
      Breakpoints.XSmall,
      Breakpoints.Small,
      Breakpoints.Medium,
      Breakpoints.Large,
      Breakpoints.XLarge,
    ]).subscribe(result => {
      if (result.matches) {
        if (result.breakpoints[Breakpoints.XSmall]) {
          this.cols = this.gridByBreakpoint.xs;
        }
        if (result.breakpoints[Breakpoints.Small]) {
          this.cols = this.gridByBreakpoint.sm;
        }
        if (result.breakpoints[Breakpoints.Medium]) {
          this.cols = this.gridByBreakpoint.md;
        }
        if (result.breakpoints[Breakpoints.Large]) {
          this.cols = this.gridByBreakpoint.lg;
        }
        if (result.breakpoints[Breakpoints.XLarge]) {
          this.cols = this.gridByBreakpoint.xl;
        }
      }
    });
   }

  ngOnInit(): void {
    const username = this.route.snapshot.paramMap.get('username');
    if (username !== null) {
      this.getUserData(username);  
    }
  }

  openDialog(): void {
    this.dialog.open(DialogOverviewExampleDialog);
  }

  getUserData(username: String){
    this.userService.getUser(username).subscribe(data => {
      this.user = data;
    });
  }
}

@Component({
  selector: 'dialog-overview-example-dialog',
  templateUrl: './dialog-overview-example-dialog.html',
})
export class DialogOverviewExampleDialog {

  imgFile!: String;
  fileData!: Blob;

  uploadForm = new FormGroup({
    file: new FormControl('', [Validators.required]),
    description: new FormControl('', [Validators.nullValidator])
  });

  constructor(
    public dialogRef: MatDialogRef<DialogOverviewExampleDialog>,
    private userService: UserService,
    @Inject(MAT_DIALOG_DATA) public data: DialogData) { }

  get uf() {
    return this.uploadForm.controls;
  }

  onImageChange(e: any) {
    const reader = new FileReader();
    
    if(e.target.files && e.target.files.length) {
      this.fileData = e.target.files[0];
      reader.readAsDataURL(this.fileData);
    
      reader.onload = () => {
        this.imgFile = reader.result as string;
        this.uploadForm.patchValue({
          imgSrc: reader.result
        });
   
      };
    }
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  submit(){
    console.log(this.uploadForm.get('file'));

    var formData: any = new FormData();
    formData.append("photo", this.fileData);
    formData.append("description", this.uploadForm.get('description')?.value);

    this.userService.postPhoto(formData).subscribe(
      (response: any) => this.reloadPage(),
      (error: any) => console.log(error)
    )
  }

  reloadPage(): void {
    window.location.reload();
  }

}
