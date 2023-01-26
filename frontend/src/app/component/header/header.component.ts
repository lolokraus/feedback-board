import {Component, OnInit} from '@angular/core';
import {AuthService, UserService} from '../../service';
import {Router} from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { CreateFeedbackModalComponent } from './create-feedback-modal/create-feedback-modal.component';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  dialogValue: string;

  constructor(
    public dialog: MatDialog,
    private userService: UserService,
    private authService: AuthService,
    public router: Router
  ) {
  }

  ngOnInit() {
  }

  hasSignedIn() {
    return !!this.userService.currentUser;
  }

  userName() {
    const user = this.userService.currentUser;
    return user.firstname;
  }

  createFeedback(): void {
    const dialogRef = this.dialog.open(CreateFeedbackModalComponent, {
      width: '450px',
      backdropClass: 'custom-dialog-backdrop-class',
      panelClass: 'custom-dialog-panel-class'
    });

    /*dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed', result);
    });*/
  }
}
