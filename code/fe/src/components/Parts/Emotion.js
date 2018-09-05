import React from 'react';
import {Alert, Divider} from 'antd';

class Emotion extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            result : this.props.result,
        };
    }
 
    render(){
        // if you want to add more paragraph, just add <p>...</p>
        let description = (
            <div>
                {this.state.result >= 0.8 ? (
                <div>
                    <p>课程情况良好</p>
                    <p>出席人数多，课堂气氛良好</p>
                </div>)
                    : (this.state.result >= 0.5) ? (
                <div>
                    <p>课程情况一般</p>
                    <p>出席人数较少，或课堂气氛异常</p>
                </div>)
                    : (
                <div>
                    <p>课堂情况糟糕</p>
                    <p>出席人数严重不足，或课堂氛围十分糟糕</p>
                </div>)}
            </div>
        );


        return(
            <div>
                <Alert
                    message="学生专注度分析"
                    description= {description}
                    type= {this.state.result >= 0.8 ? "success" : (this.state.result >= 0.5) ? "warning" : "error" }
                />
            </div> 
        );
    }
}

export default Emotion;