import React, { Component } from 'react';
import { Table} from 'antd';
import PropTypes from 'prop-types'
import{Link} from 'react-router-dom'

/**
 * input props:
 * size={"default"}/size={"middle"}/size={"small"} change the size of the table
 * (bordered={true}) to show border
 * title={"this is title"} to add a title
 * pageSize={10} to make 10 objects to be showed in a page
 * path = {path} path contains a key and a path ,it shows the address linked to
 * column={columns} the header of the table
 * data={data} the body of the table
 *
 */
class PaginationTable extends Component {

    static defaultProps={
        bordered: false,
        pagination:{position: 'bottom',pageSize:10},
        size:"default",
        title: undefined,
        showHeader:true,
    };
    static propTypes ={
        bordered: PropTypes.bool,
        pagination:PropTypes.object,
        size: PropTypes.string,
        title: PropTypes.string,
        column:PropTypes.array,
        data:PropTypes.array
    };
    constructor(props){
        super(props);
        let column = [];
        let path = this.props.path;
        this.props.column.forEach((columnItem) => {
            let aColumn = {
                title: columnItem.title,
                dataIndex: columnItem.title.toLowerCase(),
                key: columnItem.title.toLowerCase()
            };
            if(columnItem.type==='link') {
                Object.assign(aColumn, {
                    render: (text, record, index) => <Link to={path[index].pathName}>{text}</Link>
                });
            }
            column.push(aColumn);
        });
        this.state = {
            bordered: this.props.bordered,
            loading: true,
            pagination:{position: 'bottom',pageSize:this.props.pageSize},
            size: this.props.size,
            title: this.props.title ? this.getTitle.bind(this):undefined,
            columns:column,
            dataSource:this.props.data
        };
    }
    componentWillMount(){
        this.setState({
            loading:true
        })
    }
    componentDidMount(){

        this.setState({
            loading:false
        })
    }
    getTitle (){
        return this.props.title;
    };

    render() {
        return (
            <div>
                <Table {...this.state} />
            </div>
        );
    }
}

export default PaginationTable;

