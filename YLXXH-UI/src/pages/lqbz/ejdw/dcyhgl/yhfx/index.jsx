import React, {useRef, useState,useEffect} from 'react';
import {PageHeaderWrapper} from '@ant-design/pro-layout';
import ProTable from '@ant-design/pro-table';
import {pageEjdwYhfx} from "@/services/lqbz/dcyhgl";
import {DatePicker,Table} from 'antd' ;
import moment from 'moment';

const { RangePicker } = DatePicker;



const Dcyhgl = () =>{

  const actionRef = useRef() ;
  const dateFormat = 'YYYY-M';
  const [startDate,handleStartDate] = useState(moment().startOf('year').format(dateFormat)) ;
  const [endDate,handleEndDate] =useState(moment().endOf('month').format(dateFormat)) ;
  const [dataSource,handleDataSource] =useState() ;
  const onSelect = async (date, dateStrings) => {
    handleStartDate(dateStrings[0]) ;
    handleEndDate(dateStrings[1]) ;

    const response = await pageEjdwYhfx({startDate:dateStrings[0],endDate:dateStrings[1]})
    return response;

  };

  // useEffect(()=>{
  //   const lostData =async () =>{
  //     const response = await pageEjdwYhfx({startDate,endDate})
  //     return response;
  //   }
  //   lostData().then((res) =>{
  //     handleDataSource(res.data)
  //   })
  // },[startDate,endDate])



  /**
   * 单元格合并处理
   * @param isMergedFlag 合并标记，当该值相同时合并
   * @param data 当前分页所有数据
   * @param key 当前列的dataIndex
   * @param index 当前数据所在下标
   * @returns {rowSpan} 待合并单元格数量
   */
  // const mergeCells = (isMergedFlag,data, key, index) => {
  //   if(data.length > 0  && data[index] !== undefined){
  //     // 上一行该列数据是否一样
  //     if (index !== 0 && isMergedFlag === data[index - 1][key]) {
  //       return 0
  //     }
  //     let rowSpan = 1
  //     // 判断下一行是否相等
  //     for (let i = index + 1; i < data.length; i++) {
  //       if (isMergedFlag !== data[i][key]) {
  //         break
  //       }
  //       rowSpan++
  //     }
  //     return rowSpan
  //   }else{
  //     return 0;
  //   }
  //
  // }
  //
  // /**
  //  * 单元格合并渲染函数
  //  * @param value 当前单元格值
  //  * @param row 当前行
  //  * @param index 当前行索引
  //  * @returns {{children: *, props: {}}}
  //  */
  // const rowSpanRender = (value, row, index) =>{
  //   const obj = {
  //     children: value,
  //     props: {},
  //   };
  //   obj.props.rowSpan = mergeCells(row.isMergedFlag, data, 'isMergedFlag', index) ;
  //   return obj;
  // }

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
    { title: '单位名称', dataIndex: 'yingyoudanwei',},
    { title: '类型', dataIndex: 'leixing' },
    { title: '车牌号', dataIndex: 'numberplate'},
    { title: '型号 ', dataIndex: 'xinghao' ,hideInSearch : true,},
    { title: '排量 ', dataIndex: 'pailiang' ,},
    { title: '油品', dataIndex: 'youpin' ,},
    { title: '行驶里程（公里）', dataIndex: 'xingshilicheng' ,hideInSearch : true,},
    { title: '摩托小时（小时）', dataIndex: 'motuoxiaoshi' ,hideInSearch : true,},
    { title: '耗油量（升）', dataIndex: 'haoyouliang' ,hideInSearch : true,},
    { title: '消耗标准（升/百公里）', dataIndex: 'youhaobiaozhun' ,hideInSearch : true,},
    { title: '执行标准（升/百公里）', dataIndex: 'zhixingbiaozhun' ,hideInSearch : true,
      // render: (text, record,index) => (
      //   <span style={{color : rowClassName(record,index)}}> {text} </span>
      // ),
      render: (text, record,index) => {
        return toFixed(record.zhixingbiaozhun,2)
      },
      className:'red'
    },
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

      {/*<Table*/}
      {/*  rowClassName={record => {*/}
      {/*    if (record.numberplate === '辽A1001') return 'background-color: #F5222D;';*/}
      {/*  }}*/}

      {/*  rowKey={record => record.id}*/}
      {/*  columns={columns}*/}
      {/*  dataSource={dataSource}*/}
      {/*/>*/}

    </PageHeaderWrapper>
  )
}

export default Dcyhgl ;
