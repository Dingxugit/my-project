import React, {useRef, useState} from 'react';
import {PageHeaderWrapper} from '@ant-design/pro-layout';
import ProTable from '@ant-design/pro-table';
import {Button} from 'antd';
import {page} from "@/services/swy/ylzb";
import ModalImport from "./components/import";
import ExportModal from "./components/ExportModal";

const Ylzbgl = () =>{
  const actionRef = useRef() ;
  const [modalImportVisible, handleModalImportVisible] = useState(false);
  const [exportModalVisible, handleExportModalVisible] = useState(false);

  const columns = [
    { title: '用油单位', dataIndex: 'yongyoudanwei'},
    { title: '日期', dataIndex: 'riqi'},
    { title: '凭证号', dataIndex: 'pingzhenghao',hideInSearch : true },
    { title: '摘要', dataIndex: 'zhaiyao' ,hideInSearch : true},
    { title: '油品', dataIndex: 'youpin' ,hideInSearch : true},
    { title: '油料指标', hideInSearch : true,children:[{
        title: '收入', dataIndex: 'youliaozhibiaoshouru',
      },{
        title: '支出', dataIndex: 'youliaozhibiaozhichu',
      },{
        title: '结存', dataIndex: 'youliaozhibiaojeicun',
      }
      ]},

  ]


  return (
    <PageHeaderWrapper>
      <ProTable
        headerTitle="价拨/实物油料账"
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

export default Ylzbgl ;
