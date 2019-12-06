import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MarkT from './mark-t';
import MarkTDetail from './mark-t-detail';
import MarkTUpdate from './mark-t-update';
import MarkTDeleteDialog from './mark-t-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MarkTUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MarkTUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MarkTDetail} />
      <ErrorBoundaryRoute path={match.url} component={MarkT} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={MarkTDeleteDialog} />
  </>
);

export default Routes;
