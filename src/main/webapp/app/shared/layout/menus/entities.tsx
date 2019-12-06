import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown icon="th-list" name="Entities" id="entity-menu">
    <MenuItem icon="asterisk" to="/detail-t">
      Detail T
    </MenuItem>
    <MenuItem icon="asterisk" to="/node-t">
      Node T
    </MenuItem>
    <MenuItem icon="asterisk" to="/module-t">
      Module T
    </MenuItem>
    <MenuItem icon="asterisk" to="/part-t">
      Part T
    </MenuItem>
    <MenuItem icon="asterisk" to="/model-t">
      Model T
    </MenuItem>
    <MenuItem icon="asterisk" to="/type-t">
      Type T
    </MenuItem>
    <MenuItem icon="asterisk" to="/mark-t">
      Mark T
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
