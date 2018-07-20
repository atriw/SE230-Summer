import React, { Component } from 'react';
import ReactEcharts from 'echarts-for-react';
import PropTypes from 'prop-types';

/* *
 * Time: 2018/7/8
 * Author: Li Jun
 * Params:
 *     title: title name
 *     type: bar or line
 *     data: the data to be shown, in the format of [{time: 'yyyy-mm-dd hh:mm:ss', value: num},{}...]
 *     style: the style of the container of charts
 *     color: color of bars
 *     yValueName: label of y axis
 *
 * Intro: This component shows a statistic chart which contains input data
 * Return: a statistic chart component
 */
class StatChart extends Component {
    extractFormattedTime(timeString) {
        let timePattern = /(\d{4})-(\d{2})-(\d{2}) (\d{2}):(\d{2}):(\d{2})/;
        let resArr = timePattern.exec(timeString);
        if (!resArr) {
            return {};
        }
        let fields = ['year', 'month', 'day', 'hour', 'minute', 'second'];
        return Object.assign({}, ...(fields.map(function (field, index) {
            return {[field]: resArr[index+1]}
        })))
    }

    constructTimeString(timeObject) {
        let year = timeObject.year ? timeObject.year + '-' : '';
        let month = timeObject.month ? timeObject.month + '-' : '';
        let day = timeObject.day ? timeObject.day + '\n' : '';
        let hour = timeObject.hour ? timeObject.hour + ':' : '';
        let minute = timeObject.minute ? timeObject.minute + ':' : '';
        let second = timeObject.second ? timeObject.second : '';
        return year + month + day + hour + minute + second;
    }

    extractXAxis(data) {
        let timeArr = data.map((p) => (p.time));
        let curTime;
        timeArr.forEach((time, idx) => {
            let timeObject = this.extractFormattedTime(time);
            let timeObjectSave = Object.assign({}, timeObject);
            if (curTime) {
                if (curTime.year === timeObject.year) {
                    delete timeObject.year;
                    if (curTime.month === timeObject.month) {
                        delete timeObject.month;
                        if (curTime.day === timeObject.day) {
                            delete timeObject.day;
                            if (curTime.hour === timeObject.hour) {
                                delete timeObject.hour;
                                if (curTime.minute === timeObject.minute) {
                                    delete timeObject.minute;
                                }
                            }
                        }
                    }
                }
            }
            timeArr[idx] = this.constructTimeString(timeObject);
            curTime = timeObjectSave;
        });
        return timeArr;
    }

    getOption() {
        let opt = {
            title: {
                text: this.props.title
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
                name: this.props.yValueName,
                type: this.props.type,
                data: this.props.data,
                itemStyle: {
                    color: this.props.color
                }
            }]
        };
        return opt;
    }

    render() {
        return (
            <div style={this.props.style}>
                <ReactEcharts option={this.getOption()} />
            </div>
        )
    }
}

StatChart.propTypes = {
    title: PropTypes.string,
    type: PropTypes.oneOf(['line', 'bar']),
    data: PropTypes.arrayOf(PropTypes.shape({
        time: PropTypes.string,
        value: PropTypes.number
    })),
    style: PropTypes.object,
    color: PropTypes.string,
    yValueName: PropTypes.string
};

StatChart.defaultProps = {
    title: '时间-人脸数统计',
    type: 'bar',
    style: {
        width: '100%',
        height: '100%'
    },
    color: '#4169E1',
    yValueName: '人脸数'
};

export default StatChart;