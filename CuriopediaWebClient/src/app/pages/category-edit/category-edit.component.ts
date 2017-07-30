import {Component, OnInit} from "@angular/core";
import {FormGroup, FormControl, Validators} from "@angular/forms";
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

  constructor(private categoryService: CategoryService,
              private toastService: ToastService) {
  }

  ngOnInit(): void {
    this.initForm();
  }

  onSubmit(params) {
    values(this.categoryForm.controls).forEach(c => c.markAsTouched());

    if (!this.categoryForm.valid) return;

    this.categoryService.create(params)
      .subscribe(() => {          
          this.toastService.success('Successfully updated.');
        }, e => this.handleError(e));      
  }

  private initForm() {    
    this.name = new FormControl('', Validators.compose([
      Validators.required,
      Validators.minLength(4),
    ]));
    this.description = new FormControl('', Validators.compose([
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
}  