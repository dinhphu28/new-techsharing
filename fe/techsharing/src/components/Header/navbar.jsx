import React, { useState } from 'react';
// import PropTypes from 'prop-types';
import { Button, Collapse, Nav, Navbar, NavbarBrand, NavbarToggler, NavItem, NavLink } from 'reactstrap';
import AddArticleBtn from './addArticleBtn';
import "./navbarStyles.css"
import { useNavigate } from 'react-router-dom';

// NavBar.propTypes = {};

function SignInUpNav(props) {
    return (
        <>
            <NavLink href="/sign-in">Sign In</NavLink>
            <NavLink href="/sign-up">Sign Up</NavLink>
        </>
    );
}

function UserAvtNav(props) {

    // const [reloadToggle, setReloadToggle] = useState(false);

    let navigate = useNavigate();

    const logOutHandler = () => {
        localStorage.removeItem("username");
        localStorage.removeItem("token");
        localStorage.removeItem("role");

        props.onHandleChange();

        navigate("/articles");
    };

    return (
        <div className="user-avatar-nav">
            <NavLink href="#">  {/* Navigate to user's profile */}
                <img src="https://cly.1cdn.vn/2021/01/03/iu.jpg" alt="Avatar" />
            </NavLink>

            <Button
                color="primary"
                onClick={() => logOutHandler()}
            >
                Sign out
            </Button>
        </div>
    );
}

function NavBar(props) {

    const [reloadToggle, setReloadToggle] = useState(false);

    const receiveData = () => {
        setReloadToggle(!reloadToggle);
    };

    return (
        <div className="my-navbar">
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

                {/* <AddArticleBtn /> */}
                {(localStorage.getItem("role") === "mod") ? <AddArticleBtn /> : ""}
                
                {(localStorage.getItem("username") !== null) ? <UserAvtNav onHandleChange={receiveData} /> : <SignInUpNav />}
                {/* <NavLink href="/sign-in">Sign In</NavLink>
                <NavLink href="/sign-up">Sign Up</NavLink> */}
                </Collapse>
            </Navbar>
        </div>
    );
}

export default NavBar;