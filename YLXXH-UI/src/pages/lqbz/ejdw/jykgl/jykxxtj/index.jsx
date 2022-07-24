import React, {useRef, useState} from 'react';
import {page} from "@/services/lqbz/jykgl/jykxxtj";
import ModalImport from "./components/import";
import {PageHeaderWrapper} from '@ant-design/pro-layout';
import ProTable from '@ant-design/pro-table';
import {Button} from 'antd';
import PicModal from "./components/PicModal";
import ExportModal from "./components/ExportModal";


const Jykxxtj = () =>{
  const actionRef = useRef() ;
  const [modalImportVisible, handleModalImportVisible] = useState(false);
  const [picModalVisible,handlePicModalVisible] = useState(false) ;
  const [carNum,handleCarNum] = useState('') ;
  const [exportModalVisible, handleExportModalVisible] = useState(false);

  const columns = [
    { title: '加油卡卡号', dataIndex: 'fuelcardnumber'},
    { title: '车牌号', dataIndex: 'numberplate',
      render: (text, record) => (
        <a
          onClick={() => {
            handlePicModalVisible(true) ;
            handleCarNum(record.numberplate)
          }}
        > {text} </a>
      )},
    { title: '厂牌型号', dataIndex: 'brandmodel',hideInSearch : true},
    { title: '发动机号', dataIndex: 'enginenumber' ,hideInSearch : true},
    { title: '车架号', dataIndex: 'framenumber' ,hideInSearch : true},
    { title: '剩余指标', dataIndex: 'shengyuzhibiao' ,hideInSearch : true},
    { title: '确认签字', dataIndex: 'querequanzu' ,hideInSearch : true},
    { title: '填报时间', dataIndex: 'tianbaoshijian' ,hideInSearch : true},

  ]

  return (
    <PageHeaderWrapper>
      <ProTable
        headerTitle="加油卡信息统计表"
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
            onClick={() => { handleExportModalVisible(true); }}
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
      {picModalVisible ? (
        <PicModal
          onCancel={() => {
            handlePicModalVisible(false);
          }}
          picModalVisible={picModalVisible}
          carNum={carNum}
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
export default Jykxxtj ;
