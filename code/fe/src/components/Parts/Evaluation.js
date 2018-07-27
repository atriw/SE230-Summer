import React from 'react';
import {Alert, Divider} from 'antd';


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
            total:this.props.total
        };
    }

    

    render(){
        let description1 = (
            <div>
            <p>{"实际签到人数/应到人数：" + this.state.maxNum + "/" + this.state.total}</p>
            <p>{((this.state.maxNum/this.state.total) >= 0.8 ? "很不错！" : ((this.state.maxNum/this.state.total) >= 0.5) ? "糟糕！":"很糟糕")}</p>
            </div>
        );

        let description2 = (
            <div>
            <p>{"听课人数的平均比例：" + this.state.average }</p>
            <p>{"在" + this.state.max + "时，听课人数比例最多，为" + this.state.maxNum + "人。" }</p>
            <p>{"在" + this.state.min + "时，听课人数比例最少，为" + this.state.minNum + "人。"}</p>
            </div>
        );


        return(
            <div>
                <Divider orientation="left"><h2>{"第" + this.state.index + "次上课情况总结"}</h2></Divider>
                <Alert
                    message="签到人数分析"
                    description= {description1}
                    type= {(this.state.maxNum/this.state.total) >= 0.8 ? "success" : ((this.state.maxNum/this.state.total) >= 0.5) ? "warning" : "error" }
                />
                <Alert
                    message="上课情况分析"
                    description={description2}
                    type={this.state.average>="80%" ? "success" : this.state.average >= "50%" ? "warning" : "error" }
                />
            </div> 
        );
    }
}

export default Evaluation;