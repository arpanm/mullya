import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Actor from './actor';
import Requirement from './requirement';
import OTP from './otp';
import OTPAttempt from './otp-attempt';
import Address from './address';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}actor`} component={Actor} />
      <ErrorBoundaryRoute path={`${match.url}requirement`} component={Requirement} />
      <ErrorBoundaryRoute path={`${match.url}otp`} component={OTP} />
      <ErrorBoundaryRoute path={`${match.url}otp-attempt`} component={OTPAttempt} />
      <ErrorBoundaryRoute path={`${match.url}address`} component={Address} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
