import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ModuleT from './module-t';
import ModuleTDetail from './module-t-detail';
import ModuleTUpdate from './module-t-update';
import ModuleTDeleteDialog from './module-t-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ModuleTUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ModuleTUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ModuleTDetail} />
      <ErrorBoundaryRoute path={match.url} component={ModuleT} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ModuleTDeleteDialog} />
  </>
);

export default Routes;
