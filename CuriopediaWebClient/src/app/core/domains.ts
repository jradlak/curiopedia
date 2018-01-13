export interface User {
  id:string|number;
  email?:string;
  name?:string;
  authority?:string;
}

export interface Authority {
  name?:string;
}

export interface Category {
  id:string|number;
  name:string;
  description?:string;
}