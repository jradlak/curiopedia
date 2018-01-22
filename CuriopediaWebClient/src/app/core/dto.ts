export interface Page<T> {
  content: T[];
  totalPages: number;
  totalElements: number;
}

export interface PageRequest {
  page: number;
  size: number;
}

export interface UserParams {
  email?: string;
  password?: string;
  name?: string;
  isGuest?: boolean;
  authority?: string;
}

export interface CategoryParams {
  id:string|number;
  name:string;
  description?:string;
}
