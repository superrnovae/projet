import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { lastValueFrom } from 'rxjs';
import { PostService } from 'src/app/services/post.service';
import { SnackBarService } from 'src/app/services/snackbar.service';

@Component({
    selector: 'userpage-upload',
    templateUrl: './upload.component.html',
    styleUrls: ['./upload.component.css']
})
export class UploadComponent {
    
    imageFile!: string;
    fileData!: Blob;
    isSending!: boolean;
    isSent: boolean = false;

    uploadForm = new FormGroup({
        file: new FormControl('', [Validators.required]),
        description: new FormControl('', [Validators.nullValidator])
    });

    constructor(
        public dialogRef: MatDialogRef<UploadComponent>,
        private postService: PostService,
        private sbService: SnackBarService
    ) {}

    get uf() {
        return this.uploadForm.controls;
    }

    on_file_change(e: any) {
        const reader = new FileReader();

        if (e.target.files && e.target.files.length) {
            this.fileData = e.target.files[0];
            reader.readAsDataURL(this.fileData);

            reader.onload = () => {
                this.imageFile = reader.result as string;
                this.uploadForm.patchValue({
                    imgSrc: reader.result
                });
            };
        }
    }

    async submit() {
        if (this.uploadForm.invalid) {
            return;
        }
        var formData: any = new FormData();
        formData.append('photo', this.fileData);
        formData.append('description', this.uploadForm.get('description')?.value);
        this.isSending = true;
        lastValueFrom(await this.postService.postPhoto(formData))
        .then(
            (_data) => {
                this.isSent = true;
                this.close_dialog();
            }
        )
        .catch(
            (e) => {
                this.sbService.showSnackbar(JSON.stringify('POST request failed with status code ' + e.status), 'Dismiss', 7000);
            }
        )
        .finally(() => this.isSending = false)
    }

    close_dialog() {
        this.dialogRef.close(this.isSent);
    }


}
