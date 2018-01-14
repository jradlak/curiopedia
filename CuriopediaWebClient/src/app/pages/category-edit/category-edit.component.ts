import {Component, OnInit} from "@angular/core";
import {FormGroup, FormControl, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import values from "lodash/values";
import {Category} from "../../core/domains";
import {CategoryService} from "../../core/services/category.service";
import {ToastService} from "../../components/toast/toast.service";

@Component({
  selector: 'mpt-category-edit',
  templateUrl: 'category-edit.component.html',
})
export class CategoryEditComponent implements OnInit {

  categoryForm: FormGroup;
  name: FormControl;
  description: FormControl

  editOther: boolean = false;

  category: Category;

  constructor(private route: ActivatedRoute, 
              private categoryService: CategoryService,
              private toastService: ToastService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.category = <Category>{};
    this.initForm();

    this.route.params.subscribe(params => {
      if (params['id']) {
        this.categoryService.get(params['id']).subscribe(c => {          
          this.category = c;
          this.editOther = true;
          console.log(this.category);
          this.loadCategoryControlsData();
        });
      } else {
        this.loadCategoryControlsData();
      }
    });
  }

  onSubmit(params) {
    values(this.categoryForm.controls).forEach(c => c.markAsTouched());

    if (!this.categoryForm.valid) return;

    this.categoryService.create(params)
      .subscribe(() => {          
          this.toastService.success('Successfully created.');
          this.router.navigate(['/categories']); 
        }, e => this.handleError(e));      
  }

  private initForm() {    
    this.name = new FormControl(this.category.name, Validators.compose([
      Validators.required,
      Validators.minLength(4),
    ]));
    this.description = new FormControl(this.category.description, Validators.compose([
      Validators.required,      
    ]));
    
    this.categoryForm = new FormGroup({
      name: this.name,
      description: this.description,      
    });
  }

  private handleError(error) {    
    this.toastService.error("Something go wrong, message = " + error.message);   
  }

  private loadCategoryControlsData() {
    this.name.setValue(this.category.name);
    this.description.setValue(this.category.description);
  }
}  