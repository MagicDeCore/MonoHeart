import { ITypeT } from 'app/shared/model/type-t.model';

export interface IMarkT {
  id?: number;
  name?: string;
  typeTS?: ITypeT[];
}

export const defaultValue: Readonly<IMarkT> = {};
