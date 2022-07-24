import React, {useRef, useState} from 'react';
import {page} from "@/services/lqbz/jykgl/jykrcgl";
import JykrcglModal from "./components";
import {PageHeaderWrapper} from '@ant-design/pro-layout';
import ProTable from '@ant-design/pro-table';
import {Button} from 'antd';

import ExportModal from "./components/ExportModal.jsx";


const Jykrcgl = () =>{
  const actionRef = useRef() ;
  const [modalVisible,handleModalVisible] = useState(false) ;
  const [djId,handleDjId] = useState() ;
  const [formValues, handleFormValues] = useState({});
  const [exportModalVisible, handleExportModalVisible] = useState(false);


  const columns = [
    { title: '加油卡卡号', dataIndex: 'fuelcardnumber'},
    { title: '车牌号', dataIndex: 'numberplate'},
    { title: '油品', dataIndex: 'youpin',},
    { title: '领卡事由', dataIndex: 'lingkashiyou' ,hideInSearch : true},
    { title: '领取时间', dataIndex: 'lingqusj' ,},
    { title: '卡内油量L', dataIndex: 'kaneiyouliang' ,hideInSearch : true},
    { title: '归还时间', dataIndex: 'guihuanshijian' ,},
    { title: '卡内余额', dataIndex: 'kaneiyue' ,hideInSearch : true},
    { title: '驾驶员签字', dataIndex: 'jaishiyuanqianzi' ,hideInSearch : true},
    {
      title: '操作',
      dataIndex: 'option',
      valueType: 'option',
      render: (_, record) => (
        <a
          onClick={() => {
            handleDjId(record.id);
            handleModalVisible(true);
            handleFormValues(record);
          }}
        > 修改 </a>
      )
    },
  ]

  const handleShow = () =>{
    handleDjId(null);
    handleModalVisible(true);
    handleFormValues({});
  }



  return (
    <PageHeaderWrapper>
      <ProTable
        headerTitle="军油工程加油卡日常管理登记本"
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
            onClick={() => { handleExportModalVisible(true) }}
          >
            标准导出
          </Button>,
          <a id="downloadzbzDiv" style={{display: 'none'}}>mz</a>,
        ]}
        request={(params) => page({...params,})}
        columns={columns}
      />
      {modalVisible ? (
        <JykrcglModal
          onSubmit={() => {
            handleModalVisible(false);
            if (actionRef.current) actionRef.current.reload();
          }}
          onCancel={() => {
            handleModalVisible(false);
          }}
          modalVisible={modalVisible}
          djId={djId}
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

export default Jykrcgl ;
