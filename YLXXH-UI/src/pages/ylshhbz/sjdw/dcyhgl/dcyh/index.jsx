import React, {useRef, useState} from 'react';
import {PageHeaderWrapper} from '@ant-design/pro-layout';
import ProTable from '@ant-design/pro-table';
import {Button,DatePicker} from 'antd';
import {page,getDcyhList} from "@/services/ylshh/dckp";
import DcyhkpModal from "./components";
import ExportJsonExcel from "js-export-excel"
import moment from 'moment' ;
import {getAccessToken} from "@/utils/token";
import ExportModal from "./components/ExportModal";

const Dcyhgl = () =>{
  const actionRef = useRef() ;
  const [modalVisible,handleModalVisible] = useState(false) ;
  const [dcId,handleDcId] = useState(null) ;
  const [formValues, handleFormValues] = useState({});
  const [year,handleYear] = useState(moment(new Date()).format("yyyy"))
  const [exportModalVisible, handleExportModalVisible] = useState(false);
  const dept = JSON.parse(sessionStorage.getItem('DEPT')) ;

  const handleyear = (v) =>{
    handleYear(moment(v).format("yyyy"))
  }

  const columns = [
    { title: '年份', dataIndex: 'year',
      initialValue:moment(new Date(),"yyyy"),
      renderFormItem: (item, {type, defaultRender
        , ...rest}) => {
        return <DatePicker  picker="year"  format="yyyy" onChange={handleyear} allowClear={false} inputReadOnly/>;
      }},
    { title: '月份', dataIndex: 'yuefen'},
    { title: '用油单位', dataIndex: 'yingyoudanwei',hideInSearch : true},
    { title: '车牌号', dataIndex: 'numberplate',},
    { title: '油品', dataIndex: 'youpin' ,},
    { title: '类型', dataIndex: 'leixing' ,hideInSearch : true},
    { title: '品牌 ', dataIndex: 'pinpai' ,hideInSearch : true},
    { title: '排量 ', dataIndex: 'pailiang' ,hideInSearch : true,},
    { title: '型号 ', dataIndex: 'xinghao' ,hideInSearch : true,},
    { title: '消耗标准（升/百公里）', dataIndex: 'youhaobiaozhun' ,hideInSearch : true,},
    { title: '执行标准（升/百公里）', dataIndex: 'zhixingbiaozhun' ,hideInSearch : true,},
    { title: '里程初读数（公里）', dataIndex: 'lichengchudu' ,hideInSearch : true,},
    { title: '里程末读数（公里）', dataIndex: 'lichengweidu' ,hideInSearch : true,},
    { title: '行驶里程（公里）', dataIndex: 'xingshilicheng' ,hideInSearch : true,},
    { title: '摩托小时（小时）', dataIndex: 'motuoxiaoshi' ,hideInSearch : true,},
    { title: '耗油量（升）', dataIndex: 'haoyouliang' ,hideInSearch : true,},
    { title: '确认签字 ', dataIndex: 'qianziname' ,hideInSearch : true,},

    {
      title: '操作',
      dataIndex: 'option',
      valueType: 'option',
      render: (_, record) => (
        <a
          onClick={() => {
            handleDcId(record.id);
            handleModalVisible(true);
            handleFormValues(record);
          }}
        > 修改 </a>
      )
    },
  ]

  const sheetHeader = [
    '年份','月份', '用油单位', '车牌号', '油品', '类型', '品牌','排量','型号',
    '消耗标准（升/百公里）','执行标准（升/百公里）','里程初读数（公里）','里程末读数（公里）',
    '行驶里程（公里）','摩托小时（小时）','耗油量（升）','确认签字',
  ]

  /**
   * 导出数据
   */
  const handleExport = async () => {
    const data = await getDcyhList() ;
    const list = Array.isArray(data.result) ? data.result : [];

    const sheetDataList = [];
    list.forEach( v => {
      sheetDataList.push({
        ...v,
      })
    })

    const option = {};
    option.fileName = `${dept.name}油料社会化保障车辆单车油耗考核卡片`;
    option.datas = [
      {
        sheetData: sheetDataList,
        sheetFilter: [
          'year','yuefen', 'yingyoudanwei', 'numberplate', 'youpin', 'leixing', 'pinpai','pailiang',
          'xinghao','youhaobiaozhun', 'zhixingbiaozhun', 'lichengchudu', 'lichengweidu', 'xingshilicheng', 'motuoxiaoshi'
          ,'haoyouliang','qianziname',
        ],
        sheetHeader,
      },
    ];
    const toExcel = new ExportJsonExcel(option);
    toExcel.saveExcel();
  }

  const handleShow = () =>{
    handleDcId(null);
    handleModalVisible(true);
    handleFormValues({});
  }


  return (
    <PageHeaderWrapper>
      <ProTable
        headerTitle="油料社会化保障车辆单车油耗考核卡片"
        actionRef={actionRef}
        rowKey="id"
        scroll={{y: 'max-content'}}
        pagination={{pageSize: 10}}
        toolBarRender={() => [
          <Button
            // icon={<VerticalAlignBottomOutlined />}
            onClick={() => { handleShow(); }}
            type="primary"
          >
            新增
          </Button>,
          <Button
            // icon={<VerticalAlignBottomOutlined />}
            onClick={() => { handleExport(); }}
          >
            数据导出
          </Button>,
          <Button
            onClick={() => { handleExportModalVisible(true)}}
          >
            标准导出
          </Button>,
          <a id="downloadzbzDiv" style={{display: 'none'}}>mz</a>,

        ]}
        request={(params) => {
          return page({...params,...{year}});
        }}
        columns={columns}
      />
      {modalVisible ? (
        <DcyhkpModal
          onSubmit={() => {
            handleModalVisible(false);
            if (actionRef.current) actionRef.current.reload();
          }}
          onCancel={() => {
            handleModalVisible(false);
          }}
          modalVisible={modalVisible}
          dcId={dcId}
          values={formValues}
        />
      ) : null}
      {exportModalVisible ? (
        <ExportModal
          onCancel={() => {
            handleExportModalVisible(false);
          }}
          exportModalVisible={exportModalVisible}
        />
      ) : null}
    </PageHeaderWrapper>
  )
}


export default Dcyhgl ;
