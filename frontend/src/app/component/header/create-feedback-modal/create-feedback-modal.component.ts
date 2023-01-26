import { Component, OnInit, Inject, Optional, ViewChild, EventEmitter, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import {MatDialog, MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { DomSanitizer } from '@angular/platform-browser';
import { HomeComponent } from 'app/home';
import { FeedBackService } from 'app/service';
import { FeedBackDto } from 'app/shared/models/feeback-dto';

@Component({
  selector: 'app-create-feedback-modal',
  templateUrl: './create-feedback-modal.component.html',
  styleUrls: ['./create-feedback-modal.component.scss'],
})
export class CreateFeedbackModalComponent implements OnInit {

  fromDialog: string;
  selectedFiles?: FileList;
  selectedFileNames: string[] = [];

  progressInfos: any[] = [];

  public feedbacks: FeedBackDto[];

  constructor(
    public dialogRef: MatDialogRef<CreateFeedbackModalComponent>,
    private snackBar: MatSnackBar,
    private feedbackService: FeedBackService,
    private _sanitizer: DomSanitizer,
    @Optional() @Inject(MAT_DIALOG_DATA) public data: any
  ) {
  }

  ngOnInit() {
  }

  feedbackForm = new FormGroup({
    name: new FormControl('', [Validators.required]),
    description: new FormControl('', [Validators.required]),
  });

    onSubmit() {
      const feedBackData = new FormData();
      feedBackData.append('file', this.selectedFiles[0], this.selectedFiles[0].name);
      feedBackData.append('name', this.feedbackForm.value.name);
      feedBackData.append('description', this.feedbackForm.value.description);

      this.feedbackService.uploadFeedback(feedBackData).subscribe({
        next: () => {
          this.snackBar.open('Feedback uploaded successfully', 'Close');
          this.dialogRef.close({ event: 'close'});

          this.feedbackService.getAll().subscribe(feedbacks => {
            this.feedbacks = feedbacks;
              feedbacks.forEach(feedback => {
                if(feedback.picByte != null){
                feedback.picByte =  this._sanitizer.bypassSecurityTrustResourceUrl('data:image/jpg;base64,'
                + feedback.picByte);
                }
              });
          });
      },
      error: () => {
        this.snackBar.open('Could not upload Feedback, please try again!', 'Close');
      }
    })
    }

  selectFiles(event: any): void {
    this.progressInfos = [];
    this.selectedFileNames = [];
    this.selectedFiles = event.target.files;

    if (this.selectedFiles && this.selectedFiles[0]) {
      const numberOfFiles = this.selectedFiles.length;
      for (let i = 0; i < numberOfFiles; i++) {
        const reader = new FileReader();

        reader.onload = (e: any) => {
          console.log(e.target.result);
        };

        reader.readAsDataURL(this.selectedFiles[i]);
        this.selectedFileNames.push(this.selectedFiles[i].name);
      }
    }
  }

  /*upload(idx: number, file: File): void {
    this.progressInfos[idx] = { value: 0, fileName: file.name };

    if (file) {
      this.uploadService.upload(file).subscribe(
        (event: any) => {
          if (event.type === HttpEventType.UploadProgress) {
            this.progressInfos[idx].value = Math.round(
              (100 * event.loaded) / event.total
            );
          } else if (event instanceof HttpResponse) {
            const msg = 'Uploaded the file successfully: ' + file.name;
            this.message.push(msg);
            this.imageInfos = this.uploadService.getFiles();
          }
        },
        (err: any) => {
          this.progressInfos[idx].value = 0;
          const msg = 'Could not upload the file: ' + file.name;
          this.message.push(msg);
        }
      );
    }
  }

  uploadFiles(): void {
    if (this.selectedFiles) {
      for (let i = 0; i < this.selectedFiles.length; i++) {
        this.upload(i, this.selectedFiles[i]);
      }
    }
  }*/

  closeDialog() {
   this.dialogRef.close({ event: 'close'});
  }
}
