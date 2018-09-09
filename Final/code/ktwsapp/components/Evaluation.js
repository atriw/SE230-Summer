import React from 'react';
import { Text, View, StyleSheet, Dimensions, ScrollView } from 'react-native';
const screenX = Dimensions.get('window').width;

class Evaluation extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            index:this.props.index + 1,
            id:this.props.id,
            datetime:this.props.datetime,
            average:this.props.average,
            maxNum:this.props.maxNum,
            max:this.props.max,
            minNum:this.props.minNum,
            min:this.props.min,
            emotion: this.props.emotion,
            total:this.props.total
        };
    }

    emotion = (emotion) =>{
        if(emotion >= 0.8)
            return <View style={styles.line}><Text>课程状况良好</Text></View>
        else if(emotion >= 0.5)
            return <View style={styles.line}><Text>课程状况一般</Text></View>
        else
            return <View style={styles.line}><Text>课程状况糟糕</Text></View>
    }

    render(){
        return(
            <View>
                <Text style={styles.title}>{"第" + this.state.index + "次上课情况总结"}</Text>
                <ScrollView style={styles.preview}>
                    <View style={styles.line}><Text style={styles.title}>签到人数分析</Text></View>
                    <View style={styles.line}><Text>{"实际签到人数/应到人数：" + this.state.maxNum + "/" + this.state.total}</Text></View>
                    <View style={styles.line}><Text>{((this.state.maxNum/this.state.total) >= 0.8 ? "很不错！" : ((this.state.maxNum/this.state.total) >= 0.5) ? "糟糕！":"很糟糕")}</Text></View>
                </ScrollView>
                <ScrollView style={styles.preview}>
                    <View style={styles.line}><Text style={styles.title}>上课情况分析</Text></View>
                    <View style={styles.line}><Text>{"听课人数的平均比例：" + this.state.average }</Text></View>
                    <View style={styles.line}><Text>{"在" + this.state.max + "时，听课人数比例最多，为" + this.state.maxNum + "人。" }</Text></View>
                    <View style={styles.line}><Text>{"在" + this.state.min + "时，听课人数比例最少，为" + this.state.minNum + "人。"}</Text></View>
                </ScrollView>
                <ScrollView style={styles.preview}>
                    <View style={styles.line}><Text style={styles.title}>学生专注度分析</Text></View>
                    <View style={styles.line}><Text>专注指数:{this.state.emotion}</Text></View>
                    {this.emotion(this.state.emotion)}
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
      width:screenX
    },
    video:{
      position:'absolute',
      alignItems: 'flex-end',
      width: Dimensions.get('window').width-45,
      marginTop: 30,
    }
  });
  
export default Evaluation;