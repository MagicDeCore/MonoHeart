import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TypeT from './type-t';
import TypeTDetail from './type-t-detail';
import TypeTUpdate from './type-t-update';
import TypeTDeleteDialog from './type-t-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TypeTUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TypeTUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TypeTDetail} />
      <ErrorBoundaryRoute path={match.url} component={TypeT} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={TypeTDeleteDialog} />
  </>
);

export default Routes;
