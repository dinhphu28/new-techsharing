import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React from 'react';
// import PropTypes from 'prop-types';
import "./../../assets/styles/contact.css"
import { faFacebook, faGithub, faInstagram, faTwitterSquare } from '@fortawesome/free-brands-svg-icons';
import { faEnvelope, faMapMarkerAlt, faPhone } from '@fortawesome/free-solid-svg-icons';

// Contact.propTypes = {};

function Contact(props) {
    return (
        <div>
            <section class="contact-section" id="contact">
                <div class="row">
                    <div class="col span-1-of-3">
                        <ul class="information">
                            <li>
                                <FontAwesomeIcon icon={faMapMarkerAlt} className="small-icon" />
                                Address: 512 Nguyen Xien, Long Thanh My Ward, Thu Duc City, Ho Chi Minh City
                            </li>
                            <li>
                                <FontAwesomeIcon icon={faEnvelope} className="small-icon" />
                                Email: 18110175@student.hcmute.edu.vn
                            </li>
                            <li>
                                <FontAwesomeIcon icon={faPhone} className="small-icon" />
                                Phone Number: (+84) 902-746-276
                            </li>
                        </ul>
                        <ul class="social-icons">
                            <li>
                                <FontAwesomeIcon icon={faFacebook} />
                            </li>
                            <li>
                                <FontAwesomeIcon icon={faTwitterSquare} />
                            </li>
                            <li>
                                <FontAwesomeIcon icon={faInstagram} />
                            </li>
                            <li>
                                <FontAwesomeIcon icon={faGithub} />
                            </li>
                        </ul>
                    </div>
                </div>
            </section>
        </div>
    );
}

export default Contact;