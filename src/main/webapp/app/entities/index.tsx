import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import User from './user';
import Requirement from './requirement';
import OTP from './otp';
import OTPAttempt from './otp-attempt';
import Address from './address';
import Stock from './stock';
import BiddingDetails from './bidding-details';
import Bids from './bids';
import Order from './order';
import DeliveryDetails from './delivery-details';
import CancellationDetails from './cancellation-details';
import PaymentDetails from './payment-details';
import RemittanceDetails from './remittance-details';
import Hub from './hub';
import Catalogue from './catalogue';
import Banner from './banner';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}user`} component={User} />
      <ErrorBoundaryRoute path={`${match.url}requirement`} component={Requirement} />
      <ErrorBoundaryRoute path={`${match.url}otp`} component={OTP} />
      <ErrorBoundaryRoute path={`${match.url}otp-attempt`} component={OTPAttempt} />
      <ErrorBoundaryRoute path={`${match.url}address`} component={Address} />
      <ErrorBoundaryRoute path={`${match.url}stock`} component={Stock} />
      <ErrorBoundaryRoute path={`${match.url}bidding-details`} component={BiddingDetails} />
      <ErrorBoundaryRoute path={`${match.url}bids`} component={Bids} />
      <ErrorBoundaryRoute path={`${match.url}order`} component={Order} />
      <ErrorBoundaryRoute path={`${match.url}delivery-details`} component={DeliveryDetails} />
      <ErrorBoundaryRoute path={`${match.url}cancellation-details`} component={CancellationDetails} />
      <ErrorBoundaryRoute path={`${match.url}payment-details`} component={PaymentDetails} />
      <ErrorBoundaryRoute path={`${match.url}remittance-details`} component={RemittanceDetails} />
      <ErrorBoundaryRoute path={`${match.url}hub`} component={Hub} />
      <ErrorBoundaryRoute path={`${match.url}catalogue`} component={Catalogue} />
      <ErrorBoundaryRoute path={`${match.url}banner`} component={Banner} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
