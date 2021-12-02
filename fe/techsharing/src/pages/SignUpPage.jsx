import React, { Component } from "react";

class SignUpPage extends Component {
    constructor(props) {
        super(props);

        this.state = {
            username: "",
            password: "",
            retypePassword: ""
        };
    }

    changeInputValue(e) {
        this.setState({
            [e.target.name]: e.target.value
        });
    }

    validationForm() {
        let returnData = {
            error: false,
            msg: ""
        }

        const {username, password, retypePassword} = this.state;

        const re = /\S/;
        if(!re.test(username)) {
            returnData = {
                error: true,
                msg: 'Not match username format'
            }
        }

        // Kiểm tra password dài ít nhất 8 ký tự và Retype password khớp với password
        if(password.length < 8) {
            returnData = {
                error: true,
                msg: "Length of password must be greater than 8 character"
            }
        } else if(password !== retypePassword) {
            returnData = {
                error: true,
                msg: "Password not match"
            }
        }

        return returnData;
    }

    submitForm(e) {
        e.preventDefault();

        const validation = this.validationForm();

        if(validation.error) {
            alert(validation.msg);
        } else {
            alert("Submit form sign up success")
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
                    <div className="form-group">
                        <label htmlFor="pwd">Retype password:</label>
                        <input
                            type="password"
                            className="form-control"
                            name="retypePassword"
                            placeholder="Retype password"
                            onChange={e => this.changeInputValue(e)}
                        />
                    </div>
                    <button className="btn btn-primary" type="submit">
                        Sign Up
                    </button>
                </form>
            </div>
        )
    }
}

export default SignUpPage;