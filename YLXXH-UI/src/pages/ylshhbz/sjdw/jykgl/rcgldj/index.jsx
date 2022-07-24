import React, {useRef, useState} from 'react';
import {page} from "@/services/ylshh/jykgl/rcgldj";
import JykrcgldjModal from "./components";
import {PageHeaderWrapper} from '@ant-design/pro-layout';
import ProTable from '@ant-design/pro-table';
import {Button} from 'antd';
import ExportModal from "./components/ExportModal";

const Jykgldj = () =>{
  const actionRef = useRef() ;
  const [modalVisible,handleModalVisible] = useState(false) ;
  const [djId,handleDjId] = useState() ;
  const [formValues, handleFormValues] = useState({});
  const [exportModalVisible, handleExportModalVisible] = useState(false);

  const columns = [
    { title: '领卡事由', dataIndex: 'lingkashiyou' ,hideInSearch : true},
    { title: '领取时间', dataIndex: 'lingqushijian' ,},
    { title: '加油卡卡号', dataIndex: 'jiayoukakahao'},
    { title: '车牌号', dataIndex: 'numberplate'},
    { title: '厂牌型号', dataIndex: 'changpaixinghao',hideInSearch : true},
    { title: '油品', dataIndex: 'youpin',},
    { title: '上次里程数（km）', dataIndex: 'shangcilichengdushu' ,hideInSearch : true},
    { title: '本次里程数（km）', dataIndex: 'bencilichengdushu' ,hideInSearch : true},
    { title: '行驶里程（km）', dataIndex: 'xiingshilicheng' ,hideInSearch : true},
    { title: '本次加油量', dataIndex: 'bencijiayouliang' ,hideInSearch : true},
    { title: '本次加油金额', dataIndex: 'bencijiayoujine' ,hideInSearch : true},
    { title: '卡内余额', dataIndex: 'kaneiyue' ,hideInSearch : true},
    { title: '归还时间', dataIndex: 'guihuanshijian' ,},
    { title: '加油时间', dataIndex: 'jiayoushijian' ,},
    { title: '是否上交小票', dataIndex: 'shifoushangjiaoxiaopiao' ,hideInSearch : true},
    { title: '驾驶员签字', dataIndex: 'jiashiyanname' ,hideInSearch : true},
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
        headerTitle="油料社会化保障加油卡日常管理登记表"
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
        <JykrcgldjModal
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

export default Jykgldj ;
