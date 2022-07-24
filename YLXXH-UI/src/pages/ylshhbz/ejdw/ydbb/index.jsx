import React, {useRef,useState} from 'react';
import {PageHeaderWrapper} from '@ant-design/pro-layout';
import ProTable from '@ant-design/pro-table';
import {pageEjdwYdbb,} from "@/services/ylshh/jfzb";
import {DatePicker} from 'antd' ;
import moment from 'moment';

const { RangePicker } = DatePicker;

const JfYdbb = () =>{
  const actionRef = useRef() ;

  const dateFormat = 'YYYY-MM-DD';
  const [startDate,handleStartDate] = useState(moment().subtract(1, 'month').startOf('month').date(25).format(dateFormat)) ;
  const [endDate,handleEndDate] =useState(moment().startOf('month').date(25).format(dateFormat)) ;

  const onSelect = async (date, dateStrings) => {
    handleStartDate(dateStrings[0]) ;
    handleEndDate(dateStrings[1]) ;

    const response = await pageEjdwYdbb({startDate:dateStrings[0],endDate:dateStrings[1]})
    return response;

  };

  const columns = [
    { title: '年度', dataIndex: 'year',
      renderFormItem: (item, {type, defaultRender
        , ...rest}) => {
        return <RangePicker defaultValue={[moment(startDate, dateFormat), moment(endDate, dateFormat)]}
                            format={dateFormat} onChange={onSelect}/>;
      }},
    { title: '摘要', dataIndex: 'numberplate'},

    { title: '年度', hideInSearch : true,children:[
        { title: '年度分配', dataIndex: 'niandufenpei',hideInSearch : true },
        { title: '主卡经费', dataIndex: 'zhukajingfei' ,hideInSearch : true},
        {
          title: '92#', dataIndex: 'zhichuleixing92',
        },{
          title: '95#', dataIndex: 'zhichuleixing95',
        },{
          title: '-35#', dataIndex: 'zhichuleixing35',
        }
      ]},
    { title: '结存', dataIndex: 'unitinformation' ,hideInSearch : true},

  ]

  return (
    <PageHeaderWrapper>
      <ProTable
        headerTitle="油料经费指标数据列表"
        actionRef={actionRef}
        rowKey="id"
        scroll={{y: 'max-content'}}
        pagination={{pageSize: 10}}
        request={async (params) => {
          const response = await pageEjdwYdbb({...params,...{startDate,endDate}});
          return response ;
        }}
        columns={columns}
      />

    </PageHeaderWrapper>
  )
}

export default JfYdbb ;
