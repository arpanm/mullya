import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Catalogue from './catalogue';
import CatalogueDetail from './catalogue-detail';
import CatalogueUpdate from './catalogue-update';
import CatalogueDeleteDialog from './catalogue-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CatalogueUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CatalogueUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CatalogueDetail} />
      <ErrorBoundaryRoute path={match.url} component={Catalogue} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CatalogueDeleteDialog} />
  </>
);

export default Routes;
