import {Component} from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import {PasswordResetPopupComponent} from '../password-reset-popup/password-reset-popup.component';
import {HttpClient} from '@angular/common/http';
import {NotificationsService} from 'angular2-notifications';
import {UserContextService} from '../shared/user-context.service';

@Component({
  selector: 'login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent {

  constructor(private matDialog: MatDialog, private client: HttpClient,
              private notificationsService: NotificationsService,
              private userContextService: UserContextService) {
  }

  public passwordReset(): void {
    this.matDialog.open(PasswordResetPopupComponent, {width: '500px'})
      .afterClosed().subscribe(result => {
      if (result) {
        this.client.post('/api/shop/send-password-reset-link', result).subscribe(() => {
            this.notificationsService.success('Alles klar!', 'Wir haben Ihnen eine E-Mail zum Zurücksetzen Ihres Passworts geschickt.');
          },
          error => {
            console.log('Error password reset: ' + error.status + ', ' + error.message);
            this.notificationsService.error('Tut uns leid!', 'Es ist ein Fehler beim Zurücksetzen Ihres Passworts aufgetreten.');
          });
      }
    });
  }

  public onLoginSuccess(): void {
    this.userContextService.storeOwnerLoggedIn();
  }
}
