import AddCourse from './AddCourse';
import React, { Component } from 'react';
import { ScrollView} from 'react-native';
import ModifyInfo from './ModifyInfo';
import ShowInfo from './ShowInfo';

export default class PersonalInfo extends Component {
  render() {
    return (
      <ScrollView>
        <ShowInfo username="任爹" email="1111111111@sjtu.edu.cn" phoneNumber="123456789"/>
        <ModifyInfo />
      </ScrollView>
    );
  }
}
