import { IModelT } from 'app/shared/model/model-t.model';
import { IMarkT } from 'app/shared/model/mark-t.model';

export interface ITypeT {
  id?: number;
  name?: string;
  modelTS?: IModelT[];
  markTS?: IMarkT[];
}

export const defaultValue: Readonly<ITypeT> = {};
