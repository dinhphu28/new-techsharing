import React from 'react';
// import PropTypes from 'prop-types';
import { Collapse, Nav, Navbar, NavbarBrand, NavbarToggler, NavItem, NavLink } from 'reactstrap';
import AddArticleBtn from './addArticleBtn';
import "./navbarStyles.css"

// NavBar.propTypes = {};

function NavBar(props) {
    return (
        <div>
            <Navbar
                color="light"
                expand="md"
                // fixed="top"
                light
            >
                <NavbarBrand id="my-brand-logo" href="/">
                Tech Sharing
                </NavbarBrand>
                <NavbarToggler className="me-2" onClick={function noRefCheck(){} } />
                <Collapse navbar>
                <Nav
                    className="me-auto"
                    navbar
                >
                    <NavItem>
                    <NavLink href="https://facebook.com/dinhphu.nguyen.355">
                        Facebook
                    </NavLink>
                    </NavItem>
                    <NavItem>
                    <NavLink href="https://github.com/dinhphu28">
                        GitHub
                    </NavLink>
                    </NavItem>
                </Nav>

                <AddArticleBtn />
                <NavLink href="/sign-in">Sign In</NavLink>
                <NavLink href="/sign-up">Sign Up</NavLink>
                </Collapse>
            </Navbar>
        </div>
    );
}

export default NavBar;