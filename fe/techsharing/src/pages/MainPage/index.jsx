import React from 'react';
import ArticleCard from '../../features/ArticleCard/index.jsx';
import PaginationBar from '../../features/Pagination/index.jsx';
import NavMenu from './navMenu.jsx/index.jsx';

// import PropTypes from 'prop-types';

// MainPage.propTypes = {};

function MainPage(props) { 
    return (
        <div>
            <NavMenu />
            <ArticleCard />
            <ArticleCard />

            <PaginationBar />
        </div>
    );
}

export default MainPage;