import React, { Component } from 'react';
import StatChart from './components/StatChart'

class App extends Component {
  render() {
    let mock_data = [
      {time: '2018-08-09 20:30:11', value: 5},
      {time: '2018-08-09 20:35:14', value: 6},
      {time: '2018-08-09 20:40:40', value: 8},
      {time: '2018-08-09 20:45:40', value: 2},
      {time: '2018-08-09 20:50:40', value: 9},
      {time: '2018-08-09 20:55:40', value: 3},
      {time: '2018-08-09 21:00:40', value: 6},
      {time: '2018-08-09 21:05:40', value: 5},
      {time: '2018-08-09 21:10:40', value: 1},
      {time: '2018-08-09 21:15:40', value: 2},
      {time: '2018-08-09 21:20:40', value: 7},
      {time: '2018-08-09 21:25:40', value: 8},
      {time: '2018-08-10 21:40:40', value: 200}
    ]
    return (
      <div className="App">
        <header className="App-header">
          <h1 className="App-title">Welcome to React</h1>
        </header>
        <p className="App-intro">
          To get started, edit <code>src/App.js</code> and save to reload.
        </p>
        <StatChart style={{
          width: '600px',
          height: '400px',
          float: 'left'
        }} color='#4169E1' type='line' title='Stat' data={mock_data} yValueName='人脸数'/>
        <StatChart style={{
          width: '600px',
          height: '400px',
          float: 'right'
        }} color='#4169E1' type='bar' title='Stat' data={mock_data} yValueName='人脸数'/>
        <StatChart data={mock_data}/>
      </div>
    );
  }
}

export default App;
