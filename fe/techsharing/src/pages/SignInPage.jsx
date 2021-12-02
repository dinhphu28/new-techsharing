import React from "react";
import { Component } from "react";

class SignInPage extends Component {
    constructor(props) {
        super(props);

        this.state = {
            username: "",
            password: ""
        };
    }

    changeInputValue(e) {
        this.setState({
            [e.target.name]: e.target.value
        });
    }

    // Validation Form
    validationForm() {
        let returnData = {
            error: false,
            msg: ""
        }

        const {username, password} = this.state;

        // Kiểm tra username
        const re = /\S/;
        if(!re.test(username)) {
            returnData = {
                error: true,
                msg: 'Not match username format'
            }
        }

        // Kiểm tra password dài ít nhất 8 kí tự
        if(password.length < 8) {
            returnData = {
                error: true,
                msg: "Length of password must be greater than 8 character"
            }
        }

        return returnData;
    }

    submitForm(e) {
        e.preventDefault();

        const validation = this.validationForm();

        if(validation.error) {
            alert(validation.msg);
            // <div>
            //     <Alert>
            //         Hey! Pay attention.
            //     </Alert>
            // </div>
        } else {
            alert("Submit form sign in success");
        }
    }

    render() {
        return (
            <div className="container" style={{paddingTop: "5%"}}>
                <form
                    onSubmit={e => {
                        this.submitForm(e);
                    }}
                    >
                    <div className="form-group">
                        <label htmlFor="text">Username:</label>
                        <input
                            type="text"
                            className="form-control"
                            name="username"
                            placeholder="Enter username"
                            onChange={e => this.changeInputValue(e)}
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="pwd">Password:</label>
                        <input
                            type="password"
                            className="form-control"
                            name="password"
                            placeholder="Enter password"
                            onChange={e => this.changeInputValue(e)}
                        />
                    </div>
                    <button className="btn btn-primary" type="submit">
                        Sign In
                    </button>
                </form>
            </div>
        )
    }
}

export default SignInPage;