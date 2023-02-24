import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VinoListarComponent } from './vino-listar.component';

describe('VinoListarComponent', () => {
  let component: VinoListarComponent;
  let fixture: ComponentFixture<VinoListarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ VinoListarComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VinoListarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
