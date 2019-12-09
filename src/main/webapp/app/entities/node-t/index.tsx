import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import NodeT from './node-t';
import NodeTDetail from './node-t-detail';
import NodeTUpdate from './node-t-update';
import NodeTDeleteDialog from './node-t-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={NodeTUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={NodeTUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={NodeTDetail} />
      <ErrorBoundaryRoute path={match.url} component={NodeT} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={NodeTDeleteDialog} />
  </>
);

export default Routes;
