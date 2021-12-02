import React from 'react';
import { Nav, NavItem, NavLink } from 'reactstrap';
// import PropTypes from 'prop-types';

// NavMenu.propTypes = {};

function NavMenu(props) {
    return (
        <div>
            <Nav pills>
                <NavItem>
                    <NavLink
                        active
                        href="#"
                    >
                        Newest
                    </NavLink>
                </NavItem>

                <NavItem>
                    <NavLink href="#">
                        Front-End
                    </NavLink>
                </NavItem>

                <NavItem>
                    <NavLink href="#">
                        Back-End
                    </NavLink>
                </NavItem>

                <NavItem>
                    <NavLink href="#">
                        iOS
                    </NavLink>
                </NavItem>

                <NavItem>
                    <NavLink href="#">
                        Android
                    </NavLink>
                </NavItem>

                <NavItem>
                    <NavLink href="#">
                        Tip & Trick
                    </NavLink>
                </NavItem>

                <NavItem>
                    <NavLink
                        disabled
                        href="#"
                    >
                        Others
                    </NavLink>
                </NavItem>
            </Nav>
        </div>
    );
}

export default NavMenu;