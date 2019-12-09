import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import DetailT from './detail-t';
import DetailTDetail from './detail-t-detail';
import DetailTUpdate from './detail-t-update';
import DetailTDeleteDialog from './detail-t-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={DetailTUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={DetailTUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={DetailTDetail} />
      <ErrorBoundaryRoute path={match.url} component={DetailT} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={DetailTDeleteDialog} />
  </>
);

export default Routes;
