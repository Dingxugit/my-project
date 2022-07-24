import React, {useRef,useState} from 'react';
import {PageHeaderWrapper} from '@ant-design/pro-layout';
import ProTable from '@ant-design/pro-table';
import {pageEjdwYdbb,} from "@/services/lqbz/ylzbgl";
import {DatePicker} from 'antd' ;
import moment from 'moment';

const { RangePicker } = DatePicker;

const Ydbb = () =>{

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
    { title: '摘要', dataIndex: 'numberplate',hideInSearch : true},
    { title: '年度',hideInSearch : true,children:[
        { title: '年度分配', dataIndex: 'niandufenpei',hideInSearch : true },
        { title: '战区增拨', dataIndex: 'zengbo' ,hideInSearch : true},
        { title: '转供支出', dataIndex: 'zhuangong' ,hideInSearch : true},
        { title: '充卡支出',dataIndex: 'chongkazc', hideInSearch : true,},
        { title: '结存', dataIndex: 'unitinformation' ,hideInSearch : true},
      ]},

    { title: '备注', dataIndex: 'remark' ,hideInSearch : true},

  ]

  return (
    <PageHeaderWrapper>
      <ProTable
        headerTitle="油料指标数据列表"
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

export default Ydbb ;
