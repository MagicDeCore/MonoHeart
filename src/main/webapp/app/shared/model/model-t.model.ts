import { IPartT } from 'app/shared/model/part-t.model';
import { ITypeT } from 'app/shared/model/type-t.model';

export interface IModelT {
  id?: number;
  name?: string;
  wrong?: string;
  partTS?: IPartT[];
  typeTS?: ITypeT[];
}

export const defaultValue: Readonly<IModelT> = {};
