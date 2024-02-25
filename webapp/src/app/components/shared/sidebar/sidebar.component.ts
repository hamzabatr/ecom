import {Component, EventEmitter, Output} from '@angular/core';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent {
  @Output() emitCloseSideBar: EventEmitter<boolean> = new EventEmitter<boolean>();

  closeSideBar() : void {
    this.emitCloseSideBar.emit(true);
  }

}

