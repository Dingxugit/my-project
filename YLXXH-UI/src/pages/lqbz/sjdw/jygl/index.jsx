import React, {useRef, useState} from 'react';
import {PageHeaderWrapper} from '@ant-design/pro-layout';
import ProTable from '@ant-design/pro-table';
import {Button,Divider} from 'antd';
import {page,getCzpz} from "@/services/lqbz/jygl";
import YlgyczpzModal from "./components";
import PrintModal from './components/PrintModal'
import ExportJsonExcel from "js-export-excel"
import ExportModal from "./components/ExportModal";

const Jygl = () =>{

  const actionRef = useRef() ;
  const [modalVisible,handleModalVisible] = useState(false) ;
  const [jyId,handleJyId] = useState(null) ;
  const [formValues, handleFormValues] = useState({});
  const [printModalVisible,handlePrintModalVisible] =useState(false) ;
  const [exportModalVisible, handleExportModalVisible] = useState(false);
  const dept = JSON.parse(sessionStorage.getItem('DEPT')) ;
  const columns = [
    { title: '用油单位', dataIndex: 'yongyoudanwei',hideInSearch : true},
    { title: '充值时间', dataIndex: 'chongzhisj'},
    { title: '车牌号', dataIndex: 'numberplate',},
    { title: '油品', dataIndex: 'youpin' ,},
    { title: '数量(公斤)', dataIndex: 'shuliang' ,hideInSearch : true},
    { title: '经办人 ', dataIndex: 'jingbanren' ,hideInSearch : true},
    { title: '仪表显示公里（公里） ', hideInSearch : true,children:[
        { title: '上次', dataIndex: 'shangciyibiaoshu' ,hideInSearch : true},
        { title: '本次', dataIndex: 'benciyibiaoshu' ,hideInSearch : true},
      ]},

    {
      title: '操作',
      dataIndex: 'option',
      valueType: 'option',
      render: (_, record) => (
       <>
        <a
          onClick={() => {
            handleJyId(record.id);
            handleModalVisible(true);
            handleFormValues(record);
          }}
        > 修改 </a>
        <Divider type="vertical" />
          <a
            onClick={() => {
              handleJyId(record.id);
              handlePrintModalVisible(true);
              handleFormValues(record);
            }}
          > 打印 </a>
         </>
      )
    },
  ]

  const sheetHeader = [
    '用油单位','充值时间', '车牌号', '油品', '数量(公斤)', '经办人', '上次','本次',
  ]

  /**
   * 导出数据
   */
  const handleExport = async () => {
    const data = await getCzpz() ;
    const list = Array.isArray(data.result) ? data.result : [];

    const sheetDataList = [];
    list.forEach( v => {
      sheetDataList.push({
        ...v,
      })
    })

    const option = {};
    option.fileName = `${dept.name}油料供应充值凭证`;
    option.datas = [
      {
        sheetData: sheetDataList,
        sheetFilter: [
          'yongyoudanwei','chongzhisj', 'numberplate', 'youpin', 'shuliang', 'jingbanren', 'shangciyibiaoshu','benciyibiaoshu',
        ],
        sheetHeader,
      },
    ];
    const toExcel = new ExportJsonExcel(option);
    toExcel.saveExcel();
  }

  const handleShow = () =>{
    handleJyId(null);
    handleModalVisible(true);
    handleFormValues({});
  }



  return (
    <PageHeaderWrapper>
      <ProTable
        headerTitle="油料供应充值凭证"
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
        request={(params) => page({...params,})}
        columns={columns}
      />
      {modalVisible ? (
        <YlgyczpzModal
          onSubmit={() => {
            handleModalVisible(false);
            if (actionRef.current) actionRef.current.reload();
          }}
          onCancel={() => {
            handleModalVisible(false);
          }}
          modalVisible={modalVisible}
          jyId={jyId}
          values={formValues}
        />
      ) : null}
      {printModalVisible ? (
        <PrintModal
          onSubmit={() => {
            handlePrintModalVisible(false);
            if (actionRef.current) actionRef.current.reload();
          }}
          onCancel={() => {
            handlePrintModalVisible(false);
          }}
          printModalVisible={printModalVisible}
          jyId={jyId}
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

export default Jygl ;
