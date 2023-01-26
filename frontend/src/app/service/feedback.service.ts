import { HttpHeaders } from '@angular/common/http';
import {Injectable} from '@angular/core';
import { map } from 'rxjs/operators';
import {ApiService} from './api.service';
import {ConfigService} from './config.service';

@Injectable()
export class FeedBackService {

  constructor(
    private apiService: ApiService,
    private config: ConfigService
  ) {
  }

  uploadFeedback(feedBack: FormData) {
    return this.apiService.post(this.config.feedbackUrl + '/upload', feedBack)
      .pipe(map(() => {
        console.log('Upload Success');
      }));
  }

  getAll() {
    return this.apiService.get(this.config.feedbackUrl + '/all');
  }

  deleteFeedback(feedbackId: number) {
    return this.apiService.delete(this.config.feedbackUrl + '/delete/' + feedbackId);
  }

}
