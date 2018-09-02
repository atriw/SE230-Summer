import React from 'react';
import {
  Image,
  Platform,
  Dimensions,
  ScrollView,
  StyleSheet,
  Text,
  TouchableOpacity,
  View,
} from 'react-native';

const data = [{
  key: '1',
  id: '1',
  name: '高等数学',
  index: 'CS445',
  time: "TUE 08:00-10:00\nTHU 08:00-10:00\n",
  numOfStudent: 5,
  interval: 5,
},{
  key: '2',
  id: '2',
  name: '大学英语',
  index: 'EN211',
  time: "TUE 08:00-10:00\nTHU 08:00-10:00\n",
  numOfStudent: 5,
  interval: 5,
},]

export default class CourseScreen extends React.Component {
  static navigationOptions = {
    header: null,
  };

  renderCourse = (data) =>{
    let result = []
    data.forEach((column)=>{         
        result.push(
            <TouchableOpacity style={styles.coursePreview}
            onPress={() => this.props.navigation.navigate('CourseDetail',{
              name: column.name,
              index: column.index,
              time: column.time,
              numOfStudent: column.numOfStudent,
              interval: column.interval,
            })}>
            <View style={styles.Line}>
                <Text style={styles.title}>{column.name}</Text>
                <Text style={styles.right}>id：{column.id}</Text>
            </View>
            <View style={styles.Line}>
                <Text style={styles.left}>课程代码：{column.index}</Text>
                <Text style={styles.right}>学生数：{column.numOfStudent}</Text>
            </View>
            </TouchableOpacity>
        )
    })
    return result
}

  render() {
    return (
      <View style={styles.container}>
        <View style={styles.top}>
          <Text style={styles.topText}>
            我的课程
          </Text>
        </View>
        <ScrollView style={styles.container}>
        <View>
          {this.renderCourse(data)}
        </View>
        <View style={{height:10}}/>
        </ScrollView>

      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#F3F3F3',
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
  coursePreview: {
    backgroundColor: '#FFFFFF',
    alignSelf: 'center',
    flexDirection: 'column',
    width: Dimensions.get('window').width-40,
    height: 65,
    marginTop: 20,
    paddingLeft: 5,
    borderLeftColor: '#ffab63',
    borderLeftWidth: 5,
    elevation: 5,
  },
  title:{
    fontSize:18,
    color:'black',
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
  }
});
