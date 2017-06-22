export interface User {
  id:string|number;
  email?:string;
  name?:string;
  authority?:string;
}

export interface Authority {
  name?:string;
}
