import React from 'react';
import {
  Image,
  Platform,
  ScrollView,
  StyleSheet,
  Text,
  TouchableOpacity,
  View,
} from 'react-native';


import { Button, WhiteSpace} from 'antd-mobile-rn';
import TabBarIcon from '../components/TabBarIcon';
class Title extends React.Component{
    render(){
        return(
            <Text style = {styles.topText}>课程详情</Text>
        )
    }
}

export default class CourseDetailScreen extends React.Component {
    static navigationOptions = {
        header: null,
    }
  render() {
      const { navigation } = this.props;
      const name = navigation.getParam('name','noName')
      const index = navigation.getParam('index','noIndex')
    return (
      <View style={styles.container}>
         <View style={styles.top}>
          <Text style={styles.topText}>
            课程详情
          </Text>
        </View>
        <ScrollView style={styles.container} contentContainerStyle={styles.contentContainer}>
        <View>
          <Text>name:{JSON.stringify(name)}</Text>
          <Text>index:{JSON.stringify(index)}</Text>
        </View>
        </ScrollView>

      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: 'rgba(232,232,232, 1)',
  },
  top: {
    backgroundColor: 'rgba(155,207,246, 1)',
  },
  topText: {
    paddingTop: 20,
    paddingBottom: 20,
    textAlign: 'center',
    fontSize: 17,
    color: 'white',
  },
});
