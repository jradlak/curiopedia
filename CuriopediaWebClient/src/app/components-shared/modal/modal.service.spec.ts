import {inject, TestBed} from "@angular/core/testing";
import {DidConfirm, DidReject, ModalService} from "./modal.service";

describe('ModalService', () => {

  let modalService: ModalService;

  beforeEach(() => {
    TestBed.configureTestingModule({      
      providers: [      
        ModalService,
      ],
    });
  });
  beforeEach(inject([ModalService], (..._) => {
    [modalService] = _;
  }));

  describe('can confirm', () => {
      it ('confirms', (done) => {        
        modalService.events.subscribe(ev => {         
          expect(ev.val).toEqual({val: 'test'});
          expect(ev.lbl).toEqual('labelTest');
          expect(ev instanceof DidConfirm)
          done();
        });  

        modalService.confirm({val: 'test'}, 'labelTest');
      });
  });

  describe('can reject', () => {
      it ('rejects', (done) => {        
        modalService.events.subscribe(ev => {                   
          expect(ev instanceof DidReject)
          done();
        });  

        modalService.confirm({val: 'test1'}, 'labelTest1');
      });
  });
});
