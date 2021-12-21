import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { Translate, translate } from 'react-jhipster';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    data-cy="entity"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <>{/* to avoid warnings when empty */}</>
    <MenuItem icon="asterisk" to="/user">
      <Translate contentKey="global.menu.entities.user" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/requirement">
      <Translate contentKey="global.menu.entities.requirement" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/otp">
      <Translate contentKey="global.menu.entities.otp" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/otp-attempt">
      <Translate contentKey="global.menu.entities.otpAttempt" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/address">
      <Translate contentKey="global.menu.entities.address" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/stock">
      <Translate contentKey="global.menu.entities.stock" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/bidding-details">
      <Translate contentKey="global.menu.entities.biddingDetails" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/bids">
      <Translate contentKey="global.menu.entities.bids" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/order">
      <Translate contentKey="global.menu.entities.order" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/delivery-details">
      <Translate contentKey="global.menu.entities.deliveryDetails" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/cancellation-details">
      <Translate contentKey="global.menu.entities.cancellationDetails" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/payment-details">
      <Translate contentKey="global.menu.entities.paymentDetails" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/remittance-details">
      <Translate contentKey="global.menu.entities.remittanceDetails" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/hub">
      <Translate contentKey="global.menu.entities.hub" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/catalogue">
      <Translate contentKey="global.menu.entities.catalogue" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/banner">
      <Translate contentKey="global.menu.entities.banner" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
