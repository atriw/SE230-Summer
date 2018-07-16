import React, { Component } from 'react';
import { Table, Button, Form, Input } from 'antd';
import PropTypes from 'prop-types'
import{ Link } from 'react-router-dom'


/* Time: 2018/7/7
 * Author: HuangYouqi
 * Params:
 *     size: change the size of the table(default/middle/small)
 *     bordered: show border(true/false)
 *     title: add title ("the title name")
 *     pageSize: the num of the objects to be showed in a page (num of page)
 *     enablePage: a switch to enable pagination(true/false)
 *     enableSearch: a switch to enable search bar(true/false)
 *     column: the header of the table,an array of arrays which contain 2 elements, title(the name of the column) and link(true to make it to a link)
 *     data: the body of the table,an array of arrays which contain (x+1) elements,the number of elements defined in header(x) and id(+1)
 * Intro: This component shows a pagination table with customized columns and data
 * Return: a table component
 */
class PaginationTable extends Component {

    static defaultProps = {
        bordered: false,
        pageSize: 10,
        size: 'default',
        title: undefined,
        enablePage: true,
        enableSearchBar: true,
        showHeader: true
    };

    static propTypes ={
        bordered: PropTypes.bool,
        enablePage: PropTypes.bool,
        enableSearchBar: PropTypes.bool,
        pagination: PropTypes.object,
        size: PropTypes.string,
        title: PropTypes.string,
        column: PropTypes.array,
        data: PropTypes.array
    };

    constructor(props){
        super(props);
        let column = [];
        this.props.column.forEach((columnItem) => {
            let aColumn = {
                title: columnItem.title,
                dataIndex: columnItem.title.toLowerCase(),
                key: columnItem.title.toLowerCase()
            };
            if(columnItem.type === 'link') {
                Object.assign(aColumn, {
                    render: (text, record, index) => <Link to={'/course' + index}>{text}</Link>
                });
            }
            column.push(aColumn);
        });
        this.state = {
            bordered: this.props.bordered,
            loading: true,
            pagination: {position: this.props.enablePage ? 'bottom' : 'none', pageSize: this.props.pageSize},
            size: this.props.size,
            title: this.props.title ? this.getTitle.bind(this) : undefined,
            columns: column,
            dataSource:this.props.data
        };
    }

    componentWillMount(){
        this.setState({
            loading:true
        });
    }

    componentDidMount(){
        this.setState({
            loading:false
        });
    }

    getTitle(){
        return this.props.title;
    };

    handleChange = (e) => {
        this.setState({
            preSearchData : e.target.value
        });
    };

    toggleSearch = () => {
        if(!this.state.preSearchData){
            this.setState({
                dataSource: this.props.data
            });
            return;
        }
        let needle = this.state.preSearchData.toLowerCase();
        let searchData = this.props.data.filter(
            (row) => {
                return row.name.toString().toLowerCase().indexOf(needle) > -1;
            }
        );
        this.setState({
            dataSource : searchData
        });
    };

    /**
     * restore the table to the unfiltered one
     */
    restore = () => {
        this.setState({
            dataSource : this.props.data
        });
    };

    render(){
        return (
            <div>
                {this.props.enableSearchBar ? <Form layout="inline">
                    <Form.Item>
                        <Input placeholder="请输入名称" onChange={this.handleChange}/>
                    </Form.Item>
                    <Form.Item>
                        <Button type="primary" onClick={this.toggleSearch}>搜索</Button>
                    </Form.Item>
                    <Form.Item>
                        <Button type="primary" onClick={this.restore}>还原</Button>
                    </Form.Item>
                </Form> : ''}
                <Table {...this.state} />
            </div>
        );
    }
}

export default PaginationTable;

