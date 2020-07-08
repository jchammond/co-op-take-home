import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import Data from '../components/Data';

class HomePage extends Component {
  render() {
    return (
        <div className="home-page">
          <Data />
        </div>
    );
  }
}

export default withRouter(HomePage);
