import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {Category} from "../../core/domains";
import {CategoryService} from "../../core/services/category.service";
import {HttpErrorHandler} from "../../core/services/http-error-handler";
import {styles} from './category-list.component.styles';

import {ModalService, DidConfirm, DidReject} from "../../components-shared/modal/modal.service";
import isEmpty from "lodash/isEmpty";
import omitBy from "lodash/omitBy";

import {ToastService} from "../../components/toast/toast.service";

@Component({
  selector: 'mpt-category-list',
  templateUrl: 'category-list.component.html',
})
export class CategoryListComponent implements OnInit {

  styles: any = styles;
  categories: Category[];
  totalPages: number;
  page: number;

  constructor(private categoryService: CategoryService,
              private modalService: ModalService,
              private errorHandler: HttpErrorHandler,
              private route: ActivatedRoute,
              private toastService: ToastService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.page = +(params['page'] || 1);
      this.list(this.page);
    });

    this.modalService.events.subscribe((event) => {
      if (event instanceof DidConfirm) {
        this.onConfirm(event);
      } else if (event instanceof DidReject) {
        this.onReject(event);
      }
    });
  }
  
  onAdd() {
    this.router.navigate(['/categories/edit']); 
  }

  onEdit(c) {
    console.log(c);
    this.router.navigate(['/categories/edit', {id: c.id}]); 
  }

  onConfirm(confirm: DidConfirm) {
    console.log(confirm);
    if (confirm.lbl === 'delete') {
      this.deleteCategory(confirm.val);    
    }
  }

  onReject(reject: DidReject) {
    console.log(reject);
    this.list(this.page);
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

  private deleteCategory(cat) {
    this.categoryService.delete(omitBy({
      id: cat.id,
      name: cat.name,
      description: cat.description
    }, isEmpty)).subscribe(() => {
      console.log('Successfully deleted.');
      this.toastService.success('Successfully deleted.');
      this.list(this.page);
    }, e => console.log(e));
  }
}