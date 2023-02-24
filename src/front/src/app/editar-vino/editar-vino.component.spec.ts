import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditarVinoComponent } from './editar-vino.component';

describe('EditarVinoComponent', () => {
  let component: EditarVinoComponent;
  let fixture: ComponentFixture<EditarVinoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditarVinoComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditarVinoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
