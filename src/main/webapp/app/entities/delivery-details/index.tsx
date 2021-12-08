import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import DeliveryDetails from './delivery-details';
import DeliveryDetailsDetail from './delivery-details-detail';
import DeliveryDetailsUpdate from './delivery-details-update';
import DeliveryDetailsDeleteDialog from './delivery-details-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={DeliveryDetailsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={DeliveryDetailsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={DeliveryDetailsDetail} />
      <ErrorBoundaryRoute path={match.url} component={DeliveryDetails} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={DeliveryDetailsDeleteDialog} />
  </>
);

export default Routes;
