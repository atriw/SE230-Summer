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
import Request from '../request'

const data = [{
  key: '1',
  id: '1',
  name: '任锐',
  courseNum: 8,
  email: '',
  phone: '',
},{
  key: '2',
  id: '2',
  name: 'test',
  courseNum: 8,
  email: '',
  phone: '',
},{
  key: '3',
  id: '3',
  name: 'admin',
  courseNum: 8,
  email: '',
  phone: '',
},]

export default class TeacherScreen extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
        data:[],
        basedata:[],
    };
  }

  static navigationOptions = {
    header: null,
  };

  componentDidMount() {
      Request.get('/api/user/all')
        .then((res) => {
          let data = res.data;
            if (data.length > 0) {
              alert(JSON.stringify(data))
              this.setState({
                data: data,
                baseData: data,
              })
            }
          })
        .catch((error) => {
          console.log(error);
        });
    }

  renderSearchBar = () => {
    return (    
    <View style = {styles.searchContainer}>     
      <TextInput
        style = {styles.input}
        onChangeText={(text)=> this.setState({searchItem:text})}
        underlineColorAndroid='transparent'
        placeholder = '请输入要查询的教师'
      />
      <TouchableOpacity onPress={this.search} style= {styles.iconContainer}>
        <TabBarIcon name="search" ></TabBarIcon>
      </TouchableOpacity>   
    </View>
    )
  }

  search = () =>{
    if (!this.state.searchItem){
      this.setState({data:this.state.baseData})
      return
    }
    let searchData = this.state.basedata.filter(
      (row) => {
        return row.name.toString().toLowerCase().indexOf(this.state.searchItem) > -1;
      }
    );
    this.setState({data:searchData})
  }

  renderTeacher = (data) =>{
    let result = []
    data.forEach((column)=>{         
        result.push(
            <TouchableOpacity style={styles.teacherPreview}
            onPress={() => this.props.navigation.navigate('teacherDetail',{
              name: column.name,
              id: column.id,
              email: column.email,
              phone: column.phone,
            })}>
            <View style={styles.Line}>
                <Text style={styles.title}>{column.name}</Text>
                <Text style={styles.right}>id：{column.id}</Text>
            </View>
            <View style={styles.Line}>
                <Text style={styles.left}>{column.courseNum}门课程</Text>
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
            所有教师
          </Text>
        </View>
        <ScrollView style={styles.container}>
          {this.renderSearchBar()}
        <View>
          {this.renderTeacher(this.state.data)}
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
  input: {
    width: Dimensions.get('window').width-70,
  },
  iconContainer: {
    alignItems:'flex-end',
  },
  searchContainer: {
    backgroundColor: '#FFFFFF',
    alignSelf: 'center',
    flexDirection: 'row',
    alignItems: 'center',
    alignContent: 'space-between',
    marginTop: 10,
    width: Dimensions.get('window').width-40,
    elevation: 5,
  },
  teacherPreview: {
    backgroundColor: '#FFFFFF',
    alignSelf: 'center',
    flexDirection: 'column',
    width: Dimensions.get('window').width-40,
    height: 65,
    marginTop: 20,
    paddingLeft: 5,
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
