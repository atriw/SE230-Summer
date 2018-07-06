import React, { Component } from 'react';
import StatChart from './StatChart'

class Chart extends Component {
  constructor(props) {
    super(props);

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
      ];

    this.state = {
      data: mock_data
    }
  }

  componentDidMount () {
      this.interval = setInterval(() => {
          let data = this.state.data;
          data.push(data.shift());
          this.setState({
              data: data
          })
      }, 1000)
  }
  render() {

    return (
      <div className="App">
        <StatChart style={{
          width: '500px',
          height: '300px',
          float: 'left'
        }} color='#4169E1' type='line' title='Stat' data={this.state.data} yValueName='人脸数'/>
        <StatChart style={{
          width: '500px',
          height: '300px',
          float: 'right'
        }} color='#4169E1' type='bar' title='Stat' data={this.state.data} yValueName='人脸数'/>
        <StatChart data={this.state.data}/>
      </div>
    );
  }
}

export default Chart;
