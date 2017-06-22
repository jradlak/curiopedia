import {Observable} from "rxjs/Observable";
import {Subject} from "rxjs/Subject";
import {Injectable} from "@angular/core";

@Injectable()
export class ModalService {

  private modalEvents: Subject<ModalEvent>;

  constructor() {
    this.modalEvents = new Subject<ModalEvent>();
  }

  confirm(value: any, label: string): void {
    this.modalEvents.next(new DidConfirm(value, label));
  }

  reject(value: any, label: string): void {
    this.modalEvents.next(new DidReject(value, label));
  }

  get events(): Observable<ModalEvent> {
    return this.modalEvents;
  }
}

export class ModEvent {
  private value: any;  
  private label: string
  constructor(value: any, label: string) {
    this.value = value;    
    this.label = label;
  }

  get val(): any {
    return this.value;
  }
  
  get lbl(): string {
    return this.label;
  }
}

export class DidConfirm extends ModEvent {
    constructor(value: any, label: string) {
      super(value, label);
    }
}

export class DidReject extends ModEvent {
    constructor(value: any, label: string) {
      super(value, label);
    }
}

export type ModalEvent = DidConfirm | DidReject;

