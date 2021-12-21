import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale from './locale';
import authentication from './authentication';
import applicationProfile from './application-profile';

import administration from 'app/modules/administration/administration.reducer';
import userManagement from 'app/modules/administration/user-management/user-management.reducer';
import register from 'app/modules/account/register/register.reducer';
import activate from 'app/modules/account/activate/activate.reducer';
import password from 'app/modules/account/password/password.reducer';
import settings from 'app/modules/account/settings/settings.reducer';
import passwordReset from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import user from 'app/entities/user/user.reducer';
// prettier-ignore
import requirement from 'app/entities/requirement/requirement.reducer';
// prettier-ignore
import oTP from 'app/entities/otp/otp.reducer';
// prettier-ignore
import oTPAttempt from 'app/entities/otp-attempt/otp-attempt.reducer';
// prettier-ignore
import address from 'app/entities/address/address.reducer';
// prettier-ignore
import stock from 'app/entities/stock/stock.reducer';
// prettier-ignore
import biddingDetails from 'app/entities/bidding-details/bidding-details.reducer';
// prettier-ignore
import bids from 'app/entities/bids/bids.reducer';
// prettier-ignore
import order from 'app/entities/order/order.reducer';
// prettier-ignore
import deliveryDetails from 'app/entities/delivery-details/delivery-details.reducer';
// prettier-ignore
import cancellationDetails from 'app/entities/cancellation-details/cancellation-details.reducer';
// prettier-ignore
import paymentDetails from 'app/entities/payment-details/payment-details.reducer';
// prettier-ignore
import remittanceDetails from 'app/entities/remittance-details/remittance-details.reducer';
// prettier-ignore
import hub from 'app/entities/hub/hub.reducer';
// prettier-ignore
import catalogue from 'app/entities/catalogue/catalogue.reducer';
// prettier-ignore
import banner from 'app/entities/banner/banner.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const rootReducer = {
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  user,
  requirement,
  oTP,
  oTPAttempt,
  address,
  stock,
  biddingDetails,
  bids,
  order,
  deliveryDetails,
  cancellationDetails,
  paymentDetails,
  remittanceDetails,
  hub,
  catalogue,
  banner,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
};

export default rootReducer;
