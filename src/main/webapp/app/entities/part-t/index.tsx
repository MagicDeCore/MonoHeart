import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PartT from './part-t';
import PartTDetail from './part-t-detail';
import PartTUpdate from './part-t-update';
import PartTDeleteDialog from './part-t-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PartTUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PartTUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PartTDetail} />
      <ErrorBoundaryRoute path={match.url} component={PartT} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={PartTDeleteDialog} />
  </>
);

export default Routes;
