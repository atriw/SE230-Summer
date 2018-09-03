import React from 'react';
import {
  Image,
  Platform,
  ScrollView,
  Dimensions,
  StyleSheet,
  Text,
  TouchableOpacity,
  View,
} from 'react-native';
import Icon from 'react-native-vector-icons/MaterialIcons'
const screenX = Dimensions.get('window').width;
const screenY = Dimensions.get('window').height;
const testData = [
  {
      "photoId": 3,
      "timestamp": 1531677812272,
      "stats": [
          {
              "id": 30000,
              "numOfFace": 100,
              "type": "ALL"
          }
      ]
  },
  {
      "photoId": 4,
      "timestamp": 1531677813272,
      "stats": [
          {
              "id": 30001,
              "numOfFace": 200,
              "type": "ALL"
          }
      ]
  },
  {
      "photoId": 5,
      "timestamp": 1531677814272,
      "stats": [
          {
              "id": 30002,
              "numOfFace": 300,
              "type": "ALL"
          }
      ]
  }
]



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
      const time = navigation.getParam('time').replace('\n',' ')
      const id = navigation.getParam('id')
      const numOfStudent = navigation.getParam('numOfStudent')
      return (
      <ScrollView style={styles.container}>
        <View style={styles.top}>
          <TouchableOpacity style={styles.addCourse}
          onPress={() => this.props.navigation.navigate('Courses')}>
          <Icon name='chevron-left' style={{fontSize:25}} color='white'/>
          </TouchableOpacity>
          <View style={styles.titleCenter}>
            <Text style={styles.topText}>
              {name}
            </Text>
            </View>
        </View>
        <ScrollView style={styles.preview}>
            <View style={styles.Line}>
                <Text style={styles.title}>{name}</Text>
                <Text style={styles.right}>id：{id}</Text>
            </View>
            <View style={styles.Line}>
                <Text style={styles.left}>课程代码：{index}</Text>
                <Text style={styles.right}>学生数：{numOfStudent}</Text>
            </View>
            <View style={styles.Line}>
                <Text style={styles.left}>授课时间：{time}</Text>
            </View>
        </ScrollView>
        <TouchableOpacity style={styles.preview}
        onPress={() => this.props.navigation.navigate('Data',{
          data: testData,
          title: '上一次课的统计数据'
        })}>
            <View style={styles.lastCourse}>
                <Text style={{fontSize:17,color:'black'}}>上一次课的统计</Text>
            </View>
            <View style={styles.chart}>


            </View>
        </TouchableOpacity>
        <TouchableOpacity style={styles.preview}
        onPress={() => this.props.navigation.navigate('Data',{
          data: testData,
          title: '上三次课的统计数据'
        })}>
            <View style={styles.lastThreeCourse}>
                <Text style={{fontSize:17,color:'black'}}>上三次课的统计</Text>
            </View>
            <View style={styles.chart}>

            
            </View>
        </TouchableOpacity>
        
        <View style={{height:10}}/>
      </ScrollView>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    backgroundColor: 'rgba(232,232,232, 1)',
  },
  titleCenter: {
    width: screenX,
    position:'absolute',
    alignItems:'center',
    paddingTop: 20,
    paddingBottom: 20,
  },
  top: {
    backgroundColor: 'rgba(155,207,246, 1)',
    flexDirection: 'row',
    paddingTop: 20,
    paddingBottom: 20,
  },
  topText: {
    fontSize: 17,
    color: 'white',
  },
  Line:{
    flexDirection: 'row',
    alignItems: 'center',
    marginTop:5,
    marginBottom:5,
  },
  left:{
    color:'grey',
  },
  right:{
    color:'grey',
    left: 20,
  },
  title:{
    fontSize:18,
    color:'black',
  },
  preview:{
    backgroundColor: '#FFFFFF',
    alignSelf: 'center',
    flexDirection: 'column',
    width: Dimensions.get('window').width-40,
    marginTop: 20,
    paddingLeft: 5,
    elevation: 5,
  },
  chart:{
    height:200,
  }
});
