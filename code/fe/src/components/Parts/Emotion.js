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
                    <p>说明1</p> 
                    <p>说明1</p>
                </div>)
                    : (this.state.result >= 0.5) ? (
                <div>
                    <p>说明2</p> 
                    <p>说明2</p>
                </div>)
                    : (
                <div>
                    <p>说明3</p> 
                    <p>说明3</p>
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