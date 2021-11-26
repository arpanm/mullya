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
    <MenuItem icon="asterisk" to="/actor">
      <Translate contentKey="global.menu.entities.actor" />
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
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
