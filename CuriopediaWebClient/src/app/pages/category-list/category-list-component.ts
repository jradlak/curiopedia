import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {Category} from "../../core/domains";
import {CategoryService} from "../../core/services/category.service";
import {HttpErrorHandler} from "../../core/services/http-error-handler";
import {styles} from './user-list.component.styles';

@Component({
  selector: 'mpt-category-list',
  templateUrl: 'category-list.component.html',
})
export class UserListComponent implements OnInit {

  styles: any = styles;
  categories: Category[];
  totalPages: number;
  page: number;

  constructor(private categoryService: CategoryService,
              private errorHandler: HttpErrorHandler,
              private route: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.page = +(params['page'] || 1);
      this.list(this.page);
    });
  }

  onPageChanged(page: number) {
    this.router.navigate(['/categories', {page: page}]);
  }

  private list(page: number) {
    this.categoryService.list({page: page, size: 5})
      .subscribe(categoriesPage => {
        this.categories = categoriesPage.content;
        this.totalPages = categoriesPage.totalPages;
      }, e => this.errorHandler.handle(e))
    ;
  }

}
