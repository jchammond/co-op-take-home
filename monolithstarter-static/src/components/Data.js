import React, { Component } from 'react';
import { findDuplicates } from "../actions/findDuplicatesAction";

/*
 * Fns to render the records in a slightly more readable form
 */
function renderRecord(record) {
  return ( 
      <p>{record.first_name} {record.last_name}, {record.company}, {record.email}, {record.address1}, {record.address2}, {record.city}, {record.state}, {record.state_long}, {record.zip}, {record.phone}</p>
  );
}

function renderDuplicates(duplicates) {
  return (
    <div>
      <b>Duplicates:</b><br />
      {duplicates.map(pair =>
        <div>
          {renderRecord(pair[0])}
          {renderRecord(pair[1])}
          ---
        </div>
      )}
      
    </div>
  );
}

function renderNonDuplicates(nonduplicates) {
  return (
    <div>
      <b>Non-duplicates:</b><br />
      {nonduplicates.map(renderRecord)}
    </div>
  );
}

class Data extends Component {
  constructor(props) {
    super(props);
    this.state = {
      duplicates: [],
      nonduplicates: []
    };
  }

  componentDidMount() {
    this._isMounted = true;
     findDuplicates().then(dataObject => {
      if (this._isMounted)
        this.setState({
          duplicates: dataObject.duplicates,
          nonduplicates: dataObject.nonduplicates
        });
     }).catch(() => {});
  }

  componentWillUnmount() {
    this._isMounted = false;
  }

  render() {
    return (
      <div>
        {renderDuplicates(this.state.duplicates)}
        <br />
        {renderNonDuplicates(this.state.nonduplicates)}
      </div>
    );
  }
}

export default Data;