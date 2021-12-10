import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import React, { useEffect, useState } from "react";
import { faCaretUp } from '@fortawesome/free-solid-svg-icons';
import { faCaretDown } from '@fortawesome/free-solid-svg-icons';
import ReactMarkdown from "react-markdown";
import remarkGfm from "remark-gfm";
// import rehypeRaw from "rehype-raw";
import "./ArticleContent.css";
import Comments from "../Comments";
import userVoteStateApi from '../../api/userVoteStateApi';

function ArticleContent(props) {
    // constructor(props) {
    //     super(props);
    // }

    const {article} = props;

    // let tmpVoteScore = 0;
    // tmpVoteScore = article.voteScore;

    // const {title, author, content, dateCreated} = this.props;

    const [voteState, setVoteState] = useState(0);
    const [tmpVoteScore, setTmpVoteScore] = useState(article.voteScore);
    // const [refresh, setRefresh] = useState(false);

    useEffect(() => {

        if(localStorage.getItem("username") !== null) {
            fetchGetUVS();
        }

    // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);

    const fetchGetUVS = async () => {
        try {  
            const params = {
                username: localStorage.getItem("username")
            };

            const response = await userVoteStateApi.get(article.id, params);

            console.log("Fetch get UVS successfully: ", response);

            setVoteState(response.voteState);

        } catch (error) {
            console.log("Failed to fetch get UVS", error);
        }
    };

    const fetchUpdateUVS = async (vState) => {
        if(localStorage.getItem("username") !== null) {
            try {
                const params = {
                    username: localStorage.getItem("username")
                };
    
                const data = {
                    voteState: vState
                };
    
                await userVoteStateApi.put(article.id, params, data);
    
                // console.log("Fetch update UVS successfully: ", response);
                
    
            } catch (error) {
                console.log("Failed to fetch update UVS: ", error);
            }
        }
    };

    return (
        <div className="article">
            <h1 id="article-title">{article.title}</h1>
            <div className="article-info">
                <span>Written by: {article.author}</span>
                <span>Published: {article.dateCreated}</span>
                <span className="vote">
                    <FontAwesomeIcon
                        icon={faCaretUp}
                        color={(voteState === 1) ? "#04d28f" : "#888888"}
                        onClick={() => {
                            if(localStorage.getItem("username") !== null) {
                                if(voteState === 1) {
                                    setVoteState(0);
                                    fetchUpdateUVS(0);
                                    setTmpVoteScore(tmpVoteScore - 1);
                                    // setRefresh(!refresh);
                                } else if(voteState === 0) {
                                    setVoteState(1);
                                    fetchUpdateUVS(1);
                                    setTmpVoteScore(tmpVoteScore + 1);
                                    // setRefresh(!refresh);
                                }
                            }
                        }}
                    />
                    <span>{tmpVoteScore}</span>
                    <FontAwesomeIcon
                        icon={faCaretDown}
                        color={(voteState === -1) ? "#04d28f" : "#888888"}
                        onClick={() => {
                            if(localStorage.getItem("username") !== null) {
                                if(voteState === -1) {
                                    setVoteState(0);
                                    fetchUpdateUVS(0);
                                    setTmpVoteScore(tmpVoteScore + 1);
                                    // setRefresh(!refresh);
                                } else if(voteState === 0) {
                                    setVoteState(-1);
                                    fetchUpdateUVS(-1);
                                    setTmpVoteScore(tmpVoteScore - 1);
                                    // setRefresh(!refresh);
                                }
                            }
                        }}
                    />
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

export default ArticleContent;