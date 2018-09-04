import React from 'react';
import { Text, View, ScrollView, StyleSheet, Dimensions, TouchableOpacity, AsyncStorage} from 'react-native';
import Icon from 'react-native-vector-icons/MaterialIcons';

export default class Setting extends React.Component {

  static navigationOptions = {
    header: null,
  }

  _signOutAsync = async () => {
    await AsyncStorage.clear();
    this.props.navigation.navigate('Auth')
  }

  render() {
    return (
        <View style = {styles.container}>
            <View style={styles.top}>
            <TouchableOpacity
              onPress={() => this.props.navigation.navigate('Personal')}>
              <Icon name='chevron-left' style={{fontSize:25}} color='white'/>
            </TouchableOpacity>
            <View style={styles.titleCenter}>
              <Text style={styles.topText}>
                设置
              </Text>
            </View>
          </View>
        <ScrollView style={{ marginTop: 20 }}>
            <TouchableOpacity style={styles.modifyInfo} 
            onPress={() => this.props.navigation.navigate('ModifyInfo',{
              title: '修改密码'
            })}>
                <View style={styles.textContainer}><Text style={styles.bodyText}>修改密码</Text></View>
            </TouchableOpacity>
            <TouchableOpacity style={styles.modifyInfo} 
            onPress={() => this.props.navigation.navigate('ModifyInfo',{
              title: '邮箱'
            })}>
                <View style={styles.textContainer}><Text style={styles.bodyText}>邮箱</Text></View>
            </TouchableOpacity>
            <TouchableOpacity style={styles.modifyInfo} 
            onPress={() => this.props.navigation.navigate('ModifyInfo',{
              title: '手机'
            })}>
                <View style={styles.textContainer}><Text style={styles.bodyText}>手机</Text></View>
            </TouchableOpacity>
            <TouchableOpacity style={styles.signOut}
            onPress={this._signOutAsync}>
                <View style={styles.textContainer}><Text style={styles.signOutText}>退出登录</Text></View>
            </TouchableOpacity>
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
  titleCenter: {
    width: Dimensions.get('window').width,
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
  modifyInfo: {
    backgroundColor:'#FFFFFF',
    alignItems: 'flex-start',
    marginTop: 5,
    marginBottom: 5,
  },
  signOut: {
    backgroundColor:'#FFFFFF',
    alignItems: 'center',
    marginTop: 5,
    marginBottom: 5,
  },
  textContainer: {
    alignItems: 'center',
    backgroundColor:'#FFFFFF',
    marginLeft: 10,
    height:40,
  },
  bodyText: {
    marginTop: 8,
    fontSize: 16,
    color: 'black',
  },
  signOutText:{
    marginTop: 8,
    fontSize:16,
    color: 'red',
  }
});