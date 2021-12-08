import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Bids from './bids';
import BidsDetail from './bids-detail';
import BidsUpdate from './bids-update';
import BidsDeleteDialog from './bids-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BidsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BidsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BidsDetail} />
      <ErrorBoundaryRoute path={match.url} component={Bids} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BidsDeleteDialog} />
  </>
);

export default Routes;
