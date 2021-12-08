import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import RemittanceDetails from './remittance-details';
import RemittanceDetailsDetail from './remittance-details-detail';
import RemittanceDetailsUpdate from './remittance-details-update';
import RemittanceDetailsDeleteDialog from './remittance-details-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={RemittanceDetailsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={RemittanceDetailsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={RemittanceDetailsDetail} />
      <ErrorBoundaryRoute path={match.url} component={RemittanceDetails} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={RemittanceDetailsDeleteDialog} />
  </>
);

export default Routes;
