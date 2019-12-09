import { IDetailT } from 'app/shared/model/detail-t.model';
import { IModuleT } from 'app/shared/model/module-t.model';

export interface INodeT {
  id?: number;
  name?: string;
  detailTS?: IDetailT[];
  moduleTS?: IModuleT[];
}

export const defaultValue: Readonly<INodeT> = {};
