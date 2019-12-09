import { IModuleT } from 'app/shared/model/module-t.model';
import { IModelT } from 'app/shared/model/model-t.model';

export interface IPartT {
  id?: number;
  name?: string;
  moduleTS?: IModuleT[];
  modelTS?: IModelT[];
}

export const defaultValue: Readonly<IPartT> = {};
