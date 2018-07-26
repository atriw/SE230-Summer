import React from 'react';
import Evaluation from './Evaluation';

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
            total={this.props.total}
            />
        }

        return(
            <div>
            {this.props.evaluationData.map(myfunction,this)}
            </div>
        );
        
    }
}

export default Evaluations;