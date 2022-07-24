import React, {useRef, useState} from 'react';
import {PageHeaderWrapper} from '@ant-design/pro-layout';
import ProTable from '@ant-design/pro-table';
import {pageEjdwYhfx} from "@/services/ylshh/dckp";
import {DatePicker} from 'antd' ;
import moment from 'moment';

const { RangePicker } = DatePicker;

const Dcyhgl = () =>{

  const actionRef = useRef() ;
  const dateFormat = 'YYYY-M';
  const [startDate,handleStartDate] = useState(moment().startOf('year').format(dateFormat)) ;
  const [endDate,handleEndDate] =useState(moment().endOf('month').format(dateFormat)) ;

  const onSelect = async (date, dateStrings) => {
    handleStartDate(dateStrings[0]) ;
    handleEndDate(dateStrings[1]) ;

    const response = await pageEjdwYhfx({startDate:dateStrings[0],endDate:dateStrings[1]})
    return response;

  };

  const toFixed = ( dight, bits) => {
    return Math.round( dight * Math.pow( 10, bits ) ) / Math.pow( 10, bits )
  }

  const columns = [
    { title: '时间', dataIndex: 'sj',
      renderFormItem: (item, {type, defaultRender
        , ...rest}) => {
        return <RangePicker picker="month" defaultValue={[moment(startDate, dateFormat), moment(endDate, dateFormat)]}
                            format={dateFormat} onChange={onSelect}/>;
      }},
    { title: '单位名称', dataIndex: 'yingyoudanwei'},
    { title: '类型', dataIndex: 'leixing' },
    { title: '车牌号', dataIndex: 'numberplate'},
    { title: '型号 ', dataIndex: 'xinghao' ,hideInSearch : true,},
    { title: '排量 ', dataIndex: 'pailiang',},
    { title: '油品', dataIndex: 'youpin' ,},
    { title: '行驶里程（公里）', dataIndex: 'xingshilicheng' ,hideInSearch : true,},
    { title: '摩托小时（小时）', dataIndex: 'motuoxiaoshi' ,hideInSearch : true,},
    { title: '耗油量（升）', dataIndex: 'haoyouliang' ,hideInSearch : true,},
    { title: '消耗标准（升/百公里）', dataIndex: 'youhaobiaozhun' ,hideInSearch : true,},
    { title: '执行标准（升/百公里）', dataIndex: 'zhixingbiaozhun' ,hideInSearch : true,
      render: (text, record,index) => {
        return toFixed(record.zhixingbiaozhun,2)
      },},
    { title: '备注', dataIndex: 'remark' ,hideInSearch : true,},


  ]

  return (
    <PageHeaderWrapper>
      <ProTable
        headerTitle="单位常用车型单车油耗分析表"
        actionRef={actionRef}
        rowKey="sj"
        scroll={{y: 'max-content'}}
        pagination={{pageSize: 10}}
        request={async (params) => {
          const response = await pageEjdwYhfx({...params,...{startDate,endDate}});
          return response ;
        }}
        columns={columns}
      />


    </PageHeaderWrapper>
  )
}

export default Dcyhgl ;
