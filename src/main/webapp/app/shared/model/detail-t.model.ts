import { INodeT } from 'app/shared/model/node-t.model';

export interface IDetailT {
  id?: number;
  name?: string;
  nodeTS?: INodeT[];
}

export const defaultValue: Readonly<IDetailT> = {};
