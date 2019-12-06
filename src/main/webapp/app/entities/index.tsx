import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import DetailT from './detail-t';
import NodeT from './node-t';
import ModuleT from './module-t';
import PartT from './part-t';
import ModelT from './model-t';
import TypeT from './type-t';
import MarkT from './mark-t';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}detail-t`} component={DetailT} />
      <ErrorBoundaryRoute path={`${match.url}node-t`} component={NodeT} />
      <ErrorBoundaryRoute path={`${match.url}module-t`} component={ModuleT} />
      <ErrorBoundaryRoute path={`${match.url}part-t`} component={PartT} />
      <ErrorBoundaryRoute path={`${match.url}model-t`} component={ModelT} />
      <ErrorBoundaryRoute path={`${match.url}type-t`} component={TypeT} />
      <ErrorBoundaryRoute path={`${match.url}mark-t`} component={MarkT} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
