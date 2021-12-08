import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import BiddingDetails from './bidding-details';
import BiddingDetailsDetail from './bidding-details-detail';
import BiddingDetailsUpdate from './bidding-details-update';
import BiddingDetailsDeleteDialog from './bidding-details-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BiddingDetailsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BiddingDetailsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BiddingDetailsDetail} />
      <ErrorBoundaryRoute path={match.url} component={BiddingDetails} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BiddingDetailsDeleteDialog} />
  </>
);

export default Routes;
