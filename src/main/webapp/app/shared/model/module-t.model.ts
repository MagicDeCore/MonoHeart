import { INodeT } from 'app/shared/model/node-t.model';
import { IPartT } from 'app/shared/model/part-t.model';

export interface IModuleT {
  id?: number;
  name?: string;
  nodeTS?: INodeT[];
  partTS?: IPartT[];
}

export const defaultValue: Readonly<IModuleT> = {};
