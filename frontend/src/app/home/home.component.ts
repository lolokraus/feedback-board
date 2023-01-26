import {Component, Input, OnInit} from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { FeedBackDto } from 'app/shared/models/feeback-dto';
import {ConfigService, FeedBackService} from '../service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  public feedbacks: FeedBackDto[];

  constructor(
    private config: ConfigService,
    private feedbackService: FeedBackService,
    private _sanitizer: DomSanitizer,
  ) {
  }

  ngOnInit() {
    this.getFeedbacks();
  }

  getFeedbacks() {
    this.feedbackService.getAll().subscribe(feedbacks => {
      this.feedbacks = feedbacks;
      feedbacks.forEach(feedback => {
        if(feedback.picByte != null){
        feedback.picByte =  this._sanitizer.bypassSecurityTrustResourceUrl('data:image/jpg;base64,'
        + feedback.picByte);
      }
        });
    });
  }

  deleteFeedback(feedBack: FeedBackDto) {
    this.feedbackService.deleteFeedback(feedBack.id).subscribe({
      next: () => {
      this.feedbackService.getAll().subscribe(feedbacks => {
        this.feedbacks = feedbacks;
        feedbacks.forEach(feedback => {
          if(feedback.picByte != null){
          feedback.picByte =  this._sanitizer.bypassSecurityTrustResourceUrl('data:image/jpg;base64,'
          + feedback.picByte);
        }
          });
      });
    }
  })
}
}
