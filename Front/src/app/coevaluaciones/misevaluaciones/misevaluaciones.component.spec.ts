import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MisevaluacionesComponent } from './misevaluaciones.component';

describe('MisevaluacionesComponent', () => {
  let component: MisevaluacionesComponent;
  let fixture: ComponentFixture<MisevaluacionesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MisevaluacionesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MisevaluacionesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
