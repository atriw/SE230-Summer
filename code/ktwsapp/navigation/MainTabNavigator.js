import React from 'react';
import { createStackNavigator, createBottomTabNavigator } from 'react-navigation';

import TabBarIcon from '../components/TabBarIcon';
import PersonalScreen from '../screens/PersonalScreen';
import CourseScreen from '../screens/CourseScreen';
import CourseDetailScreen from '../screens/CourseDetailScreen';
import AddCourse from '../components/AddCourse'
import SettingScreen from '../screens/SettingScreen'
import ModifyInfoScreen from '../screens/ModifyInfoScreen'
import DataDisplayScreen from '../screens/DataDisplayScreen'
import PhotoDisplayScreen from '../screens/PhotoDisplayScreen'
import VideoScreen from '../screens/VideoScreen';

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
  Video: VideoScreen,
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
  PersonalStack,
});
