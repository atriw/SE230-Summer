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
import Chart from '../components/StatChart'
import Request from '../request'
const screenX = Dimensions.get('window').width;
const screenY = Dimensions.get('window').height;
const testData = [
  {
      "photoId": 1,
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
      "photoId": 2,
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
      "photoId": 3,
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
let mock_data = [
          {time: '2018-08-09 20:30:11', value: 5},
          {time: '2018-08-09 20:35:14', value: 6},
          {time: '2018-08-09 20:40:40', value: 8},
          {time: '2018-08-09 20:45:40', value: 2},
          {time: '2018-08-09 20:50:40', value: 9},
          {time: '2018-08-09 20:55:40', value: 3},
          {time: '2018-08-09 21:00:40', value: 6},
          {time: '2018-08-09 21:05:40', value: 5},
          {time: '2018-08-09 21:10:40', value: 1},
          {time: '2018-08-09 21:15:40', value: 2},
          {time: '2018-08-09 21:20:40', value: 7},
          {time: '2018-08-09 21:25:40', value: 8},
          {time: '2018-08-10 21:40:40', value: 200}
      ];


class Title extends React.Component{
    render(){
        return(
            <Text style = {styles.topText}>课程详情</Text>
        )
    }
}

export default class CourseDetailScreen extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            id: this.props.navigation.getParam('id')
        }
    }
    static navigationOptions = {
        header: null,
    }

    componentWillMount() {
        let id = this.state.id;
        let COURSE_INFO_URL = '/api/course/byCourseId?courseId=' + id;
        let LAST_THREE_COURSE_STAT_URL = '/api/stat/byLast3Courses?courseId=' + id;
        let LAST_COURSE_STAT_URL = '/api/stat/byLastCourse?courseId=' + id;
        let SECTION_STAT_URL = '/api/stat/sectionStat?courseId=' + id;
        Request.get(COURSE_INFO_URL)
            .then((res) => {
                let data = res.data;
                this.setState({
                    data: [data],
                    camera: data.camera
                })

            })
            .catch((error) => {
                console.log(error);
        });
        Request.get(LAST_THREE_COURSE_STAT_URL)
            .then((res) => {
                let data = res.data;
                if (data.length > 0) {
                    this.setState({
                        lastThreeData: this.processData(data)
                    })
                }
            })
            .catch((error) => {
                console.log(error);
        });
        Request.get(LAST_COURSE_STAT_URL)
            .then((data) => {
                let data = res.data;
                if (data.length > 0) {
                    this.setState({
                        allData: this.processData(data)
                    })
                }
            })
            .catch((error) => {
                console.log(error);
        });
        Request.get(SECTION_STAT_URL)
        .then((data) => {
            if (data.length > 0) {
                this.setState({
                    sectionStat: this.processData3(data)
                })
            }
        })
        .catch((error) => {
            console.log(error);
        });
    };

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
                <Chart data={mock_data} width={screenX}/>

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
                <Chart type={'line'} data={mock_data}/>
            
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
