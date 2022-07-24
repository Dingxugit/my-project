import React, {useRef, useState} from 'react';
import {PageHeaderWrapper} from '@ant-design/pro-layout';
import ProTable from '@ant-design/pro-table';
import {Button,DatePicker} from 'antd';
import {page} from "@/services/ylshh/dckp";
import ModalImport from "./components/import";
import moment from 'moment' ;
import ExportModal from "./components/ExportModal";

const Dcyhgl = () =>{
  const actionRef = useRef() ;
  const [modalImportVisible, handleModalImportVisible] = useState(false);
  const [year,handleYear] = useState(moment(new Date()).format("yyyy")) ;
  const [exportModalVisible, handleExportModalVisible] = useState(false);

  const handleyear = (v) =>{
    handleYear(moment(v).format("yyyy"))
  }

  const columns = [
    { title: '年份', dataIndex: 'year',
      initialValue:moment(new Date()),
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

  ]

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
        request={(params) => {

          return page({...params,...{year}});
        }}
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


export default Dcyhgl ;
