import AddCourse from './AddCourse';
import React, { Component } from 'react';
import { AppRegistry , ScrollView} from 'react-native';
import { Button } from 'antd-mobile-rn';
import ModifyCourse from './ModifyCourse';


export default class App extends Component {
  render() {
    return (
      <ModifyCourse />
    );
  }
}



