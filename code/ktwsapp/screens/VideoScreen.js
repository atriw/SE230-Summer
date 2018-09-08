import React from 'react';
import {
  Image,
  Dimensions,
  ScrollView,
  StyleSheet,
  Text,
  TouchableOpacity,
  View,
} from 'react-native';
import MaterialIcon from 'react-native-vector-icons/MaterialIcons';
import Video from 'react-native-video'

export default class VideoScreen extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
        data:[],
    };
  }

  static navigationOptions = {
    header: null,
  };


  render() {
    const camera = this.props.navigation.getParam('camera')
    return (
      <View style={styles.container}>
        <View style={styles.top}>
          <Text style={styles.topText}>
            监控画面
          </Text>
          <TouchableOpacity style={styles.return}
          onPress={() => this.props.navigation.navigate('CourseDetail')}>
          <MaterialIcon name='chevron-left' style={{fontSize:25}} color='white'/>
          </TouchableOpacity>
        </View>
        <View>
            <Video source={{uri: camera}} style={styles.Video}/>
        </View>
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
    width: Dimensions.get('window').width-5,
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
  Video:{
    width: Dimensions.get('window').width,
    height: Dimensions.get('window').width * 3 / 4
  }

});
