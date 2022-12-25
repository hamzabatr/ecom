import {Component, EventEmitter, Output} from '@angular/core';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent {
  isSideBarOpen: boolean = false;

  @Output() emitToggle: EventEmitter<boolean> = new EventEmitter<boolean>();

  toggleSideBar() : void {
    this.emitToggle.emit(!this.isSideBarOpen);
  }

}
