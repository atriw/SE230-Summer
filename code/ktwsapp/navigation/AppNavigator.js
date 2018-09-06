
import { createSwitchNavigator } from 'react-navigation';

import AuthLoadingScreen from '../auth/authloading'
import MainTabNavigator from './MainTabNavigator';
import AdminTabNavigator from './AdminTabNavigator';
import LoginScreen from '../auth/LoginScreen';
import SubmitScreen from '../auth/SubmitScreen';

export default createSwitchNavigator({
  // You could add another route here for authentication.
  // Read more at https://reactnavigation.org/docs/en/auth-flow.html
  AuthLoading: AuthLoadingScreen,
  App: MainTabNavigator,
  Admin: AdminTabNavigator,
  Auth: LoginScreen,
  Submit: SubmitScreen,
},{
  initialRouteName: 'Auth',
});