import React, { Component } from 'react';
import { Route, Switch } from 'react-router-dom';
import './App.scss';
import HomePage from "./pages/HomePage";

class App extends Component {
  render() {
    return (
      <Switch>
        <Route key="home" path="/" exact={true} component={HomePage} />
      </Switch>
    );
  }
}

export default App;
