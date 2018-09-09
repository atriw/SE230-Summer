import React from 'react';
import {
  Image,
  TextInput,
  Dimensions,
  ScrollView,
  StyleSheet,
  Text,
  TouchableOpacity,
  View,
} from 'react-native';
import TabBarIcon from '../components/TabBarIcon';
import MaterialIcon from 'react-native-vector-icons/MaterialIcons';

//let mock_data = [
//          {id: 1, time: '2018-08-09 20:30:11', value: 5},
//          {id: 2, time: '2018-08-09 20:35:14', value: 6},
//          {id: 3, time: '2018-08-09 20:40:40', value: 8}
//      ];

export default class DataDisplayScreen extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
        data:[],
    };
  }

  static navigationOptions = {
    header: null,
  };

  renderData = (data) =>{
    let result = []
    data.forEach((column)=>{  
        let time = column.time
        result.push(
            <View>
            <TouchableOpacity style={styles.Line}
            onPress={()=>this.props.navigation.navigate('Photo',{
                photoId: column.id
            })}>
                <Text style={{color:'black'}}>
                    {JSON.stringify(column.value)}
                </Text>
                <View style={styles.rightContainer}><Text style={{color:'grey'}}>{time}</Text></View>
            </TouchableOpacity>
            <View style={{height:1}}/>
            </View>
        )
    })
    return result
}

  render() {
    const data = this.props.navigation.getParam('data',[])
    const title = this.props.navigation.getParam('title','数据')
    return (
      <View style={styles.container}>
        <View style={styles.top}>
          <Text style={styles.topText}>
            {title}
          </Text>
          <TouchableOpacity style={styles.return}
          onPress={() => this.props.navigation.navigate('CourseDetail')}>
          <MaterialIcon name='chevron-left' style={{fontSize:25}} color='white'/>
          </TouchableOpacity>
        </View>
        <ScrollView style={styles.container}>
        <View style={{paddingLeft:10,height:40,paddingTop:20,}}>
        <Text style={{fontSize:14}}>人脸数</Text>
        </View>
        <View>
          {this.renderData(data)}
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
  return: {
    width: 20,
    position: 'absolute',
    alignContent: 'center',
    alignItems: 'flex-start',
    paddingTop: 20,
    paddingBottom: 20,
  },
  title:{
    fontSize:18,
    color:'black',
  },
  Line:{
    backgroundColor: '#FFFFFF',
    flexDirection: 'row',
    alignItems: 'center',
    alignContent: 'space-between',
    height:40,
    paddingLeft:10,
  },
  rightContainer:{
    position:'absolute',
    width: Dimensions.get('window').width-10,
    alignItems: 'flex-end'
  }

});
