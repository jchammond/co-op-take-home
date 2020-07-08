import React, { Component } from 'react';
import { getHelloMessage } from "../actions/helloAction";

/*
 * Render individual records in a slightly more sensible form
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
     getHelloMessage().then(dataObject => {
      if (this._isMounted)
        this.setState({
          duplicates: dataObject.duplicates,
          nonduplicates: dataObject.nonduplicates
        });
     }).catch(() => {
      if (this._isMounted)
        this.setState();
    });
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

// class Record extends React.Component {
//   constructor(props) {
//     super(props);
//     this.state = props;
//   }

//   render() {
//     return (
//       <div>
//         <div>{this.state.first_name}</div>
//         <div>{this.state.last_name}</div>
//         <div>{this.state.company}</div>
//         <div>{this.state.email}</div>
//         <div>{this.state.address1}</div>
//         <div>{this.state.address2}</div>
//         <div>{this.state.city}</div>
//         <div>{this.state.state}</div>
//         <div>{this.state.state_long}</div>
//         <div>{this.state.zip}</div>
//         <div>{this.state.phone}</div>
//       </div>
//     );
//   }
// }

export default Data;