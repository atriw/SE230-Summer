import React from 'react';
import Evaluation from './Evaluation';
import { View } from 'react-native'

class Evaluations extends React.Component{
    render(){
        function myfunction(currentValue, index) {
            console.log(currentValue);
            return <Evaluation 
            index={index}
            id={currentValue.id} 
            dateTime={currentValue.datetime}
            average={currentValue.average}
            maxNum={currentValue.maxNum}
            max={currentValue.max}
            minNum={currentValue.minNum}
            min={currentValue.min}
            emotion={currentValue.emotion}
            total={this.props.total}
            />
        }

        return(
            <View>
            {this.props.evaluationData.map(myfunction,this)}
            </View>
        );
        
    }
}

export default Evaluations;