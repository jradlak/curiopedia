import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {User, Authority} from "../../core/domains";
import {UserService} from "../../core/services/user.service";
import {AuthoritiesService} from "../../core/services/authorities.service";
import {HttpErrorHandler} from "../../core/services/http-error-handler";
import {ModalService, DidConfirm, DidReject} from "../../components-shared/modal/modal.service";

import {ToastService} from "../../components/toast/toast.service";

import {styles} from './admin-panel.component.styles';
import isEmpty from "lodash/isEmpty";
import omitBy from "lodash/omitBy";

@Component({
  selector: 'mpt-admin-panel',
  templateUrl: 'admin-panel.component.html',
})
export class AdminPanelComponent implements OnInit {

  styles: any = styles;
  users: User[];
  authorities: Authority[];
  totalPages: number;
  page: number;

  constructor(private userService: UserService,
              private modalService: ModalService,
              private authoritiesService: AuthoritiesService,
              private errorHandler: HttpErrorHandler,
              private route: ActivatedRoute,
              private router: Router,
              private toastService: ToastService) {            
    }
  
  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.page = +(params['page'] || 1);
      this.list(this.page);
      this.loadAuthorities();
    });

    this.modalService.events.subscribe((event) => {
      if (event instanceof DidConfirm) {
        this.onConfirm(event);
      } else if (event instanceof DidReject) {
        this.onReject(event);
      }
    });
  }

  onPageChanged(page: number) {
    this.router.navigate(['/adminPanel', {page: page}]);
  }

  onAuthorityChange(usr, event) {     
    usr.authority = event;   
    this.updateUser(usr);
  }

  onConfirm(confirm: DidConfirm) {
    if (confirm.lbl === 'delete') {
      this.deleteUser(confirm.val);    
    } else {
      this.onAuthorityChange(confirm.val.usr, confirm.val.event);
    }
  }

  onReject(reject: DidReject) {
    console.log(reject);
    this.list(this.page);
  }

  onEdit(usr) {
    console.log(usr);
    this.router.navigate(['/users/edit', {id: usr.id}]); 
  }

  private list(page: number) {
    this.userService.list({page: page, size: 5})
      .subscribe(usersPage => {
        this.users = usersPage.content;
        this.totalPages = usersPage.totalPages;
      }, e => this.errorHandler.handle(e))
    ;
  }

  private loadAuthorities() {
      this.authoritiesService.list()
        .subscribe(authorities => this.authorities = authorities);
  }

  private updateUser(usr) {      
      this.userService.update(omitBy({
        email: usr.email,
        password: '',
        name: usr.name,
        isGuest: false,
        authority: usr.authority             
      }, isEmpty)).subscribe(() => {          
        console.log('Successfully updated.');
        this.toastService.success('Successfully updated.');
      }, e => console.log(e));
  }

  private deleteUser(usr) {      
      this.userService.delete(omitBy({
        email: usr.email,
        password: '',
        name: usr.name,
        isGuest: false,
        authority: usr.authority             
      }, isEmpty)).subscribe(() => {
        console.log('Successfully deleted.');
        this.toastService.success('Successfully deleted.');
        this.list(this.page);
      }, e => console.log(e));
  }
}

