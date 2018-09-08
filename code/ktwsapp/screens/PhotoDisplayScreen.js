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
import Request from '../request';


export default class PhotoDisplayScreen extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
        data:[],
        photoId: 0
    };
  }

  static navigationOptions = {
    header: null,
  };

  componentWillMount(){
    this.setState({
        photoId: this.props.navigation.getParam('photoId',1)
//    }, () => {
//        Request.get( "/api/photo/byPhotoId?photoId="+this.state.photoId, {
//              responseType: "arraybuffer",
//            }).then(res => {
//              return 'data:image/png;base64,' + btoa(
//                  new Uint8Array(res.data)
//                    .reduce((data, byte) => data + String.fromCharCode(byte), '')
//                );
//            })
//            .then(data => {
//                this.state.imgsrc = data
//            })
//            .catch(ex => {
//              console.error(ex);
//            });
    })
  }

  render() {
    return (
      <View style={styles.container}>
        <View style={styles.top}>
          <Text style={styles.topText}>
            图片{this.state.photoId}
          </Text>
          <TouchableOpacity style={styles.return}
          onPress={() => this.props.navigation.navigate('Data')}>
          <MaterialIcon name='chevron-left' style={{fontSize:25}} color='white'/>
          </TouchableOpacity>
        </View>
        <Image
          alt='photo'
          source={{uri: "http://10.0.2.2:8080/api/photo/byPhotoId?photoId=" + this.state.photoId}}
          style={styles.Image}
        />

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
  Image:{
    width: Dimensions.get('window').width,
    height: Dimensions.get('window').width * 3 / 4
  }

});
