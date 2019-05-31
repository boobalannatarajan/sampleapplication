import { Category } from './create-category/category';
import { Reminder } from './create-reminder/reminder';
// Note class properties along with Reminder and Category
export class Note {
  noteId: Number;
  noteTitle: string;
  noteContent: string;
  noteStatus: string;
  noteCreatedBy: string;
  category: Category;
  reminders: Array<Reminder>;

}
