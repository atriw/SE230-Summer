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
import { Button, WhiteSpace} from 'antd-mobile-rn';
import Icon from 'react-native-vector-icons/MaterialIcons'
const screenX = Dimensions.get('window').width;
const screenY = Dimensions.get('window').height;
class Title extends React.Component{
    render(){
        return(
            <Text style = {styles.topText}>课程详情</Text>
        )
    }
}

export default class TeacherDetailScreen extends React.Component {
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
          <View>
          <Icon name='chevron-left' style={{fontSize:20}} color='white'/>
          </View>
          <View style={styles.titleCenter}>
            <Text style={styles.topText}>
              课程详情
            </Text>
            </View>
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
});
