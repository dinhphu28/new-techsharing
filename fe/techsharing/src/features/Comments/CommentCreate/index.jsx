import { faPaperPlane } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React, { useState } from 'react';
import { Button } from 'reactstrap';
import "./CommentCreate.css";
// import PropTypes from 'prop-types';
import commentApi from '../../../api/commentApi';

// CommentCreate.propTypes = {};

function CommentCreate(props) {

    const {articleId} = props;
    
    const [content, setContent] = useState("");

    const changeInputValue = (e) => {
        setContent(e.target.value);
    }

    const fetchCreateComment = async () => {
        try {
            const commentData = {
                author: localStorage.getItem("username"),
                content: content
            };

            await commentApi.post(articleId, commentData);

            // console.log("Write comment successfully: ", response);

            // props.onHandleChange();

        } catch(error) {
            console.log("Failed to fetch create comment: ", error);
        }
    }

    return (
        <div className="leave-comment">
            <img src="https://image.freepik.com/free-photo/closeup-person-holding-adorable-tiny-kitten_181624-21695.jpg" alt="Avatar" />
            <textarea
                name="comment"
                rows="4"
                placeholder="Leave comment here"
                value={content}
                onChange={(e) => changeInputValue(e)}
            ></textarea>
            <Button
                color="primary"
                type="button"
                onClick={() => {
                    fetchCreateComment();
                    setContent("");
                }}
            >
                <FontAwesomeIcon icon={faPaperPlane} />
            </Button>
        </div>
    );
}

export default CommentCreate;