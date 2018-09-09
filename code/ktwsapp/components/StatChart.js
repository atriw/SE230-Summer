import React, {Component} from 'react';
import Echarts from 'native-echarts';

export default class StatChart extends Component{
    constructor(props) {
        super(props);
        this.getOption = this.getOption.bind(this);
    }

    getOption() {
            let opt = {
                title: {
                    text: this.props.title || '时间-人脸数统计'
                },
                tooltip: {},
                xAxis: {
                    type: 'category',
                    data: this.props.data.map((p) => p.time)//this.extractXAxis(this.props.data),
                    // axisLabel: {
                    //     interval: 0,
                    //     rotate: -45
                    // }
                }
                ,
                yAxis: {},
                series: [{
                    name: this.props.yValueName || '人脸数',
                    type: this.props.type || 'bar',
                    data: this.props.data,
                    itemStyle: {
                        color: this.props.color || '#4169E1'
                    }
                }]
            };
            return opt;
        }
    render() {
        return <Echarts option={this.getOption()} height={this.props.height || 200} width={this.props.width || 200}/>
    }
}