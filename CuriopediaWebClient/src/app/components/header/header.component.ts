import {Component, OnInit, HostListener} from "@angular/core";
import {Router} from "@angular/router";
import {AuthService} from "../../core/services/auth.service";
import {styles} from "./header.component.styles";

@Component({
  selector: 'mpt-header',
  templateUrl: 'header.component.html',
})
export class HeaderComponent implements OnInit {

  styles: any = styles;
  isSignedIn: boolean;
  isMenuHidden: boolean = true;
  isGuest: boolean = false;
  isAuthor: boolean = false;
  isAdmin: boolean = false;

  constructor(private router: Router,
              private authService: AuthService) {
  }

  ngOnInit(): void {
    this.checkRoles();
    this.authService.events.subscribe(() => {
      this.checkRoles();
    });
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  toggleMenu(e: Event) {
    this.isMenuHidden = !this.isMenuHidden;
    e.stopPropagation();
  }

  @HostListener('document:click') hideMenu() {
    this.isMenuHidden = true;
  }

  private checkRoles(): void {
    this.isAuthor = this.authService.isAuthor();
    this.isSignedIn = this.authService.isSignedIn();
    this.isGuest = this.authService.isGuest();
    this.isAdmin = this.authService.isAdmin();
  }
}
