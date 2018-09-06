import React from 'react';
import {
  Image,
  Platform,
  ScrollView,
  StyleSheet,
  AsyncStorage,
  Text,
  View,
  Dimensions,
  TouchableOpacity,
} from 'react-native';
import MaterialIcon from 'react-native-vector-icons/MaterialIcons';


export default class PersonalScreen extends React.Component {
  constructor(props){
    super(props)
    this.state=({
      userName:'',
      userToken:'',
    })
  }

  static navigationOptions = {
    header: null,
  };

  componentWillMount = async() =>{
    await AsyncStorage.getItem('userName',(error,result)=>{
      this.setState({
        userName: result,
      })
    })
    await AsyncStorage.getItem('userToken',(error,result)=>{
      this.setState({
        userToken: result,
      })
    })
  }

  render() {
    return (
      <View style={styles.container}>
        <View style={styles.top}>
          <Text style={styles.topText}>
            我的
          </Text>
          <TouchableOpacity style={styles.titleEnd}
            onPress={() => this.props.navigation.navigate('Setting')}>
          <MaterialIcon name = 'settings' color = 'white' size = {28} />
          </TouchableOpacity>
          <View style={styles.userContainer}>
            <Image
              source={
                require('../assets/images/manhara.png')
              }
              style={styles.personalImage}
            />
            <View style={styles.userinfoContainer}>
              <Text style={styles.userinfoText}>{this.state.userName}</Text>
              <Text style={styles.userinfoText}>上海交通大学</Text>
            </View>
          </View>
        </View>
        <ScrollView style={styles.container} contentContainerStyle={styles.contentContainer}>


          <TouchableOpacity style={styles.selection}  onPress={() => this.props.navigation.navigate('CourseStack')} >
            <Text style={styles.selectionText}>
            {this.state.userToken==='user'?'我的课程':'所有课程'}</Text>
          </TouchableOpacity>

          <View style={{height:1}}/>

          <View style={styles.selection}>
            <Text style={styles.selectionText}>{this.state.userToken==='user'?'我的统计信息':'所有统计信息'}</Text>
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
  titleEnd: {
    width: Dimensions.get('window').width-5,
    position: 'absolute',
    alignContent: 'center',
    alignItems: 'flex-end',
    paddingTop: 16,
    paddingBottom: 20,
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
    borderRadius: 100,
  },
  selection: {
    height: 40,
    backgroundColor: 'white',
  },
  selectionText:{
    color: 'black',
    fontSize: 16,
    marginTop: 12,
    marginLeft: 10,
  },
  getStartedContainer: {
    alignItems: 'center',
    marginHorizontal: 50,
  },
  homeScreenFilename: {
    marginVertical: 7,
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
});
