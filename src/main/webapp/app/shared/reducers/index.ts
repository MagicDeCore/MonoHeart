import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import detailT, {
  DetailTState
} from 'app/entities/detail-t/detail-t.reducer';
// prettier-ignore
import nodeT, {
  NodeTState
} from 'app/entities/node-t/node-t.reducer';
// prettier-ignore
import moduleT, {
  ModuleTState
} from 'app/entities/module-t/module-t.reducer';
// prettier-ignore
import partT, {
  PartTState
} from 'app/entities/part-t/part-t.reducer';
// prettier-ignore
import modelT, {
  ModelTState
} from 'app/entities/model-t/model-t.reducer';
// prettier-ignore
import typeT, {
  TypeTState
} from 'app/entities/type-t/type-t.reducer';
// prettier-ignore
import markT, {
  MarkTState
} from 'app/entities/mark-t/mark-t.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly detailT: DetailTState;
  readonly nodeT: NodeTState;
  readonly moduleT: ModuleTState;
  readonly partT: PartTState;
  readonly modelT: ModelTState;
  readonly typeT: TypeTState;
  readonly markT: MarkTState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  detailT,
  nodeT,
  moduleT,
  partT,
  modelT,
  typeT,
  markT,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar
});

export default rootReducer;
