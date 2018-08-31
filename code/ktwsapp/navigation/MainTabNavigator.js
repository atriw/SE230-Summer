import React from 'react';
import { Platform } from 'react-native';
import { createStackNavigator, createBottomTabNavigator } from 'react-navigation';

import TabBarIcon from '../components/TabBarIcon';
import PersonalScreen from '../screens/PersonalScreen';
import LoginScreen from '../screens/LoginScreen';
import SubmitScreen from '../screens/SubmitScreen';
import CourseScreen from '../screens/CourseScreen';

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

const LinksStack = createStackNavigator({
  Links: PersonalScreen,
});

LinksStack.navigationOptions = {
  tabBarLabel: 'Links',
  tabBarIcon: ({ focused }) => (
    <TabBarIcon
      focused={focused}
      name='link'
    />
  ),
};

const SettingsStack = createStackNavigator({
  Settings: PersonalScreen,
});

SettingsStack.navigationOptions = {
  tabBarLabel: 'Settings',
  tabBarIcon: ({ focused }) => (
    <TabBarIcon
      focused={focused}
      name='settings'
    />
  ),
};

const PersonalStack = createStackNavigator({
  Personal: PersonalScreen,
  Login: LoginScreen,
  Submit: SubmitScreen,
  Course: CourseScreen
});

PersonalStack.navigationOptions = {
  tabBarLabel: 'Person',
  tabBarIcon: ({ focused }) => (
    <TabBarIcon
      focused={focused}
      name='person'
    />
  ),
};

export default createBottomTabNavigator({
  HomeStack,
  LinksStack,
  SettingsStack,
  PersonalStack,
});
