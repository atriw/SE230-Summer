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

import { MonoText } from '../components/StyledText';

import { Button, WhiteSpace} from 'antd-mobile-rn';
const data = [{
  key: '1',
  id: '1',
  name: '组合数学',
  index: 'CS445',
  time: "TUE 08:00-10:00\nTHU 08:00-10:00\n",
  numOfStudent: 5,
  interval: 5,
},{
  key: '2',
  id: '2',
  name: '环境数学模型',
  index: 'EV211',
  time: "TUE 08:00-10:00\nTHU 08:00-10:00\n",
  numOfStudent: 5,
  interval: 5,
},]


export default class CourseScreen extends React.Component {
  static navigationOptions = {
    header: null,
  };

  renderCourse = (data) =>{
    alert(data)
    data.forEach((column)=>{         
        alert(JSON.stringify(column))
        return(
            <View style={styles.selection}>
                <Text style={styles.selectionText}>test</Text>
            </View>
        )
    })
}

  render() {
    return (
      <View style={styles.container}>
        <View style={styles.top}>
          <Text style={styles.topText}>
            我的课程
          </Text>
        </View>
        <ScrollView style={styles.container} contentContainerStyle={styles.contentContainer}>
        <View>
          {this.renderCourse(data)}
        </View>

          <View style={styles.helpContainer}>

                  <Button type="primary" onClick={() => this.props.navigation.navigate('Login')}>点击登录</Button><WhiteSpace />
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
    paddingTop: 40,
    paddingBottom: 20,
    textAlign: 'center',
    fontSize: 17,
    color: 'white',
  },
  developmentModeText: {
    marginBottom: 20,
    color: 'rgba(0,0,0,0.4)',
    fontSize: 14,
    lineHeight: 19,
    textAlign: 'center',
  },
  contentContainer: {
    paddingTop: 30,
  },
  userContainer: {
    flexDirection: 'row',
    marginTop: 10,
    marginBottom: 20,
  },
  userinfoContainer:{
    marginLeft:20,
    flexDirection: 'column'
  },
  userinfoText: {
    marginTop: 10,
    fontSize: 15,
    color: 'white',
    lineHeight: 24,
  },
  personalImage: {
    width: 80,
    height: 80,
    resizeMode: 'contain',
    marginTop: 3,
    marginLeft: 10,
    borderRadius: 50,
  },
  selection: {
    height: 40,
    backgroundColor: 'white',
    shadowOffset:{width:5, height:5},
  },
  selectionText:{
    color: 'black',
    fontSize: 16,
    marginTop: 13,
    marginLeft: 10,
  },
  getStartedContainer: {
    alignItems: 'center',
    marginHorizontal: 50,
  },
  homeScreenFilename: {
    marginVertical: 7,
  },
  codeHighlightText: {
    color: 'rgba(96,100,109, 0.8)',
  },
  codeHighlightContainer: {
    backgroundColor: 'rgba(0,0,0,0.05)',
    borderRadius: 3,
    paddingHorizontal: 4,
  },
  getStartedText: {
    fontSize: 17,
    color: 'rgba(96,100,109, 1)',
    lineHeight: 24,
    textAlign: 'center',
  },
  tabBarInfoContainer: {
    position: 'absolute',
    bottom: 0,
    left: 0,
    right: 0,
    ...Platform.select({
      ios: {
        shadowColor: 'black',
        shadowOffset: { height: -3 },
        shadowOpacity: 0.1,
        shadowRadius: 3,
      },
      android: {
        elevation: 20,
      },
    }),
    alignItems: 'center',
    backgroundColor: '#fbfbfb',
    paddingVertical: 20,
  },
  tabBarInfoText: {
    fontSize: 17,
    color: 'rgba(96,100,109, 1)',
    textAlign: 'center',
  },
  navigationFilename: {
    marginTop: 5,
  },
  helpContainer: {
    marginTop: 15,
    alignItems: 'center',
  },
  helpLink: {
    paddingVertical: 15,
  },
  helpLinkText: {
    fontSize: 14,
    color: '#2e78b7',
  },
});
