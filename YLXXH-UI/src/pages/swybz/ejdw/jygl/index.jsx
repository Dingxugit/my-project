import React, {useRef, useState} from 'react';
import {PageHeaderWrapper} from '@ant-design/pro-layout';
import ProTable from '@ant-design/pro-table';
import {Button,Divider} from 'antd';
import {page} from "@/services/swy/dj";
import ModalImport from "./components/import";
import DcyhkpModadl from './components/dcyh' ;
import ExportModal from "./components/ExportModal";

const Jygl = () =>{
  const actionRef = useRef() ;
  const [modalDckpVisible, handleModalDckpVisible] = useState(false);
  const [chejiahao, handleChejiahao] = useState(false);
  const [riqi, handleRiqi] = useState(false);
  const [modalImportVisible, handleModalImportVisible] = useState(false);
  const [exportModalVisible, handleExportModalVisible] = useState(false);

  const columns = [
    { title: '用油单位', dataIndex: 'yongyoudanwei'},
    { title: '凭证号', dataIndex: 'pingzhenghao' },
    { title: '日期', dataIndex: 'riqi',hideInSearch : true},
    { title: '装备号', dataIndex: 'zhuangbeihao' ,hideInSearch : true,
      render: (text, record, index) => (
        <>
          <a
            onClick={() => {
              handleChejiahao(record.zhuangbeihao);
              handleRiqi(record.riqi) ;
              handleModalDckpVisible(true);
            }}
          > {text} </a>

        </>
      )},
    { title: '发动机号', dataIndex: 'fadongjihao' ,hideInSearch : true},
    { title: '车架号', dataIndex: 'chejiahao' ,hideInSearch : true,

    },
    { title: '油品', dataIndex: 'youpin' ,hideInSearch : true},
    { title: '加油量', dataIndex: 'youliaozhibiaojeicun' ,hideInSearch : true},
    { title: '司机签字', dataIndex: 'sijiname' ,hideInSearch : true},
    { title: '密度', dataIndex: 'midu' ,hideInSearch : true},
    { title: '加油员签字', dataIndex: 'jiayouyuanname' ,hideInSearch : true},


  ]


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
            onClick={() => { handleModalImportVisible(true); }}
          >
            数据导入
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
      {modalImportVisible ? (
        <ModalImport
          onSubmit={() => {
            handleModalImportVisible(false);

            if (actionRef.current) actionRef.current.reload();
          }}
          onCancel={() => {
            handleModalImportVisible(false);
          }}
          modalImportVisible={modalImportVisible}
        />
      ) : null}

      {modalDckpVisible ? (
        <DcyhkpModadl
          onCancel={() => {
            handleModalDckpVisible(false);
          }}
          modalDckpVisible={modalDckpVisible}
          chejiahao={chejiahao}
          riqi={riqi}
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
