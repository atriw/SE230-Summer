import React from 'react';
import { createStackNavigator, createBottomTabNavigator } from 'react-navigation';

import TabBarIcon from '../components/TabBarIcon';
import PersonalScreen from '../screens/PersonalScreen';
import CourseScreen from '../screens/CourseScreen';
import TeacherScreen from '../screens/TeacherScreen';
import CourseDetailScreen from '../screens/CourseDetailScreen';
import TeacherDetailScreen from '../screens/TeacherDetailScreen'
import AddCourse from '../components/AddCourse'
import SettingScreen from '../screens/SettingScreen'
import ModifyInfoScreen from '../screens/ModifyInfoScreen'
import DataDisplayScreen from '../screens/DataDisplayScreen'
import PhotoDisplayScreen from '../screens/PhotoDisplayScreen'

const HomeStack = createStackNavigator({
  Home: PersonalScreen,
});

HomeStack.navigationOptions = {
  tabBarLabel: 'Home',
  tabBarIcon: ({ focused }) => (
    <TabBarIcon
      focused={focused}
      name='home'
    />
  ),
};

const CourseStack = createStackNavigator({
  Courses: CourseScreen,
  AddCourse: AddCourse,
  CourseDetail: CourseDetailScreen,
  Data: DataDisplayScreen,
  Photo: PhotoDisplayScreen,
});

CourseStack.navigationOptions = {
  tabBarLabel: 'Courses',
  tabBarIcon: ({ focused }) => (
    <TabBarIcon
      focused={focused}
      name='book'
    />
  ),
};

const TeacherStack = createStackNavigator({
  Teachers: TeacherScreen,
  TeacherDetail: TeacherDetailScreen,
});

TeacherStack.navigationOptions = {
  tabBarLabel: 'Teachers',
  tabBarIcon: ({ focused }) => (
    <TabBarIcon
      focused={focused}
      name='person'
    />
  ),
};

const PersonalStack = createStackNavigator({
  Personal: PersonalScreen,
  Setting: SettingScreen,
  ModifyInfo: ModifyInfoScreen,
});

PersonalStack.navigationOptions = {
  tabBarLabel: 'Person',
  tabBarIcon: ({ focused }) => (
    <TabBarIcon
      focused={focused}
      name='panorama-fish-eye'
    />
  ),
};

export default createBottomTabNavigator({
  CourseStack,
  TeacherStack,
  PersonalStack,
});
