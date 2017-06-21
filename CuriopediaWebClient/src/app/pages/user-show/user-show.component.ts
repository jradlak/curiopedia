import {Component, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {User} from "../../core/domains";
import {UserService} from "../../core/services/user.service";
import {styles} from './user-show.component.styles';

@Component({
  selector: 'mpt-user-show',
  templateUrl: 'user-show.component.html',
})
export class UserShowComponent implements OnInit {

  styles: any = styles;
  userId: string;

  user: User;
  userName: string | undefined;
  userRole: string | undefined;
  userEmail: string | undefined;

  constructor(private route: ActivatedRoute,
              private userService: UserService) {
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.userId = params['id'];
      if (this.userId) {
        this.userService.get(this.userId).subscribe(
          u => { 
            this.user = u; 
            this.userName = this.user.name;
            this.userRole = this.user.authority;
            this.userEmail = this.user.email;
          }, err => { console.log(err); }
        );
      }
    });
  }

}
