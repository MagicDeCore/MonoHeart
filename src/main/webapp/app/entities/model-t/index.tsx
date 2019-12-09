import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ModelT from './model-t';
import ModelTDetail from './model-t-detail';
import ModelTUpdate from './model-t-update';
import ModelTDeleteDialog from './model-t-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ModelTUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ModelTUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ModelTDetail} />
      <ErrorBoundaryRoute path={match.url} component={ModelT} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ModelTDeleteDialog} />
  </>
);

export default Routes;
