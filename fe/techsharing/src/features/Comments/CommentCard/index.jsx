import React from 'react';
import "./CommentCard.css";
// import PropTypes from 'prop-types';

// CommentCard.propTypes = {};

function CommentCard(props) {

    const {comment} = props;

    return (
        <div className="comment-card">
            <div className="comment-info">
                <img src="https://image.freepik.com/free-photo/closeup-person-holding-adorable-tiny-kitten_181624-21695.jpg" alt="Avatar" />
                <span className="username">{comment.author}</span>
                <span className="datetime">{comment.date} {comment.time}</span>
            </div>
            <p>
                {comment.content}
            </p>
        </div>
    );
}

export default CommentCard;