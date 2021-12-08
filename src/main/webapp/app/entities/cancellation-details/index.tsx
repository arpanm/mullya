import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CancellationDetails from './cancellation-details';
import CancellationDetailsDetail from './cancellation-details-detail';
import CancellationDetailsUpdate from './cancellation-details-update';
import CancellationDetailsDeleteDialog from './cancellation-details-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CancellationDetailsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CancellationDetailsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CancellationDetailsDetail} />
      <ErrorBoundaryRoute path={match.url} component={CancellationDetails} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CancellationDetailsDeleteDialog} />
  </>
);

export default Routes;
