import React, {useRef, useState} from 'react';
import {PageHeaderWrapper} from '@ant-design/pro-layout';
import ProTable from '@ant-design/pro-table';
import {Button,Divider} from 'antd';
import {page} from "@/services/lqbz/jygl";
import ModalImport from "./components/import";
import DcyhkpModadl from './components/dcyh' ;
import ExportModal from "./components/ExportModal";

const Jygl = () =>{

  const actionRef = useRef() ;
  const [modalImportVisible, handleModalImportVisible] = useState(false);
  const [modalDckpVisible, handleModalDckpVisible] = useState(false);
  const [numberplate, handleNumberplate] = useState(false);
  const [chongzhisj, handleChongzhisj] = useState(false);
  const [exportModalVisible, handleExportModalVisible] = useState(false);

  const columns = [
    { title: '用油单位', dataIndex: 'yongyoudanwei',hideInSearch : true},
    { title: '充值时间', dataIndex: 'chongzhisj',hideInSearch : true},
    { title: '车牌号', dataIndex: 'numberplate',
      render: (text, record, index) => (
        <>
          <a
            onClick={() => {
              handleNumberplate(record.numberplate);
              handleChongzhisj(record.chongzhisj) ;
              handleModalDckpVisible(true);
            }}
          > {text} </a>

        </>
      )
    },
    { title: '油品', dataIndex: 'youpin' ,},
    { title: '数量(公斤)', dataIndex: 'shuliang' ,hideInSearch : true},
    { title: '经办人 ', dataIndex: 'jingbanren' ,hideInSearch : true},
    { title: '仪表显示公里（公里） ', hideInSearch : true,children:[
        { title: '上次', dataIndex: 'shangciyibiaoshu' ,hideInSearch : true},
        { title: '本次', dataIndex: 'benciyibiaoshu' ,hideInSearch : true},
      ]},

  ]

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
          numberplate={numberplate}
          chongzhisj={chongzhisj}
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
