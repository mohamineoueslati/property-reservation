import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-message',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './message.component.html',
  styleUrl: './message.component.css',
})
export class MessageComponent {
  @Input() message: string = '';
  @Input() severity: 'success' | 'failure' = 'success';
  @Input() isDisplayed: boolean = false;

  getMessageClass(): string {
    return `generic-message ${this.severity}`;
  }

  closeMessage(): void {
    this.isDisplayed = false;
  }
}
