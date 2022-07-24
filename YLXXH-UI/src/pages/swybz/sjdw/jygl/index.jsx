import React, {useRef, useState} from 'react';
import {PageHeaderWrapper} from '@ant-design/pro-layout';
import ProTable from '@ant-design/pro-table';
import {Button} from 'antd';
import {page,getDjList} from "@/services/swy/dj";
import JyglModal from "./components";
import ExportJsonExcel from "js-export-excel"
import ExportModal from "./components/ExportModal";

const Jygl = () =>{
  const actionRef = useRef() ;
  const [modalVisible,handleModalVisible] = useState(false) ;
  const [jyId,handleJyId] = useState() ;
  const [formValues, handleFormValues] = useState({});
  const dept = JSON.parse(sessionStorage.getItem('DEPT')) ;
  const [exportModalVisible, handleExportModalVisible] = useState(false);

  const columns = [
    { title: '用油单位', dataIndex: 'yongyoudanwei'},
    { title: '凭证号', dataIndex: 'pingzhenghao',hideInSearch : true },
    { title: '日期', dataIndex: 'riqi'},
    { title: '装备号', dataIndex: 'zhuangbeihao' ,hideInSearch : true},
    { title: '发动机号', dataIndex: 'fadongjihao' ,hideInSearch : true},
    { title: '车架号', dataIndex: 'chejiahao' ,hideInSearch : true},
    { title: '油品', dataIndex: 'youpin' ,hideInSearch : true},
    { title: '加油量', dataIndex: 'youliaozhibiaojeicun' ,hideInSearch : true},
    { title: '司机签字', dataIndex: 'sijiname' ,hideInSearch : true},
    { title: '密度', dataIndex: 'midu' ,hideInSearch : true},
    { title: '加油员签字', dataIndex: 'jiayouyuanname' ,hideInSearch : true},

    {
      title: '操作',
      dataIndex: 'option',
      valueType: 'option',
      render: (_, record) => (
        <a
          onClick={() => {
            handleJyId(record.id);
            handleModalVisible(true);
            handleFormValues(record);
          }}
        > 修改 </a>
      )
    },
  ]

  const sheetHeader = [
    '用油单位', '凭证号','日期', '装备号', '发动机号', '车架号','油品','加油量','司机签字','密度','加油员签字',
  ]

  /**
   * 导出数据
   */
  const handleExport = async () => {
    const data = await getDjList() ;
    const list = Array.isArray(data.result) ? data.result : [];

    const sheetDataList = [];
    list.forEach( v => {
      sheetDataList.push({
        ...v,
      })
    })

    const option = {};
    option.fileName = `${dept.name}价拨/实物油加注登记表`;
    option.datas = [
      {
        sheetData: sheetDataList,
        sheetFilter: [
          'yongyoudanwei','pingzhenghao', 'riqi', 'zhuangbeihao', 'fadongjihao',
          'chejiahao', 'youpin','youliaozhibiaojeicun','sijiname','midu','jiayouyuanname',
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
        headerTitle="价拨/实物油加注登记表"
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
        <JyglModal
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
