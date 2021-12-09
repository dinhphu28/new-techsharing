import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import React, { Component } from "react";
import { faCaretUp } from '@fortawesome/free-solid-svg-icons';
import { faCaretDown } from '@fortawesome/free-solid-svg-icons';
import ReactMarkdown from "react-markdown";
import remarkGfm from "remark-gfm";
// import rehypeRaw from "rehype-raw";
import "./ArticleContent.css";
import Comments from "../Comments";

class ArticleContent extends Component {
    // constructor(props) {
    //     super(props);
    // }

    render() {
        const {article} = this.props;

        // const {title, author, content, dateCreated} = this.props;

        return (
            <div className="article">
                <h1 id="article-title">{article.title}</h1>
                <div className="article-info">
                    <span>Written by: {article.author}</span>
                    <span>Published: {article.dateCreated}</span>
                    <span className="vote">
                        <FontAwesomeIcon icon={faCaretUp} />
                        <span>0</span>
                        <FontAwesomeIcon icon={faCaretDown} />
                    </span>
                </div>
                <hr />
                <div className="article-content">
                    <ReactMarkdown children={article.content} remarkPlugins={[remarkGfm]} />
                </div>
                <hr />
                <div className="comment">
                    <Comments articleId={article.id}/>
                </div>
            </div>
        )
    }
}

export default ArticleContent;