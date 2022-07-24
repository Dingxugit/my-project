import React, {useRef, useState} from 'react';
import {PageHeaderWrapper} from '@ant-design/pro-layout';
import ProTable from '@ant-design/pro-table';
import {Button,DatePicker} from 'antd';
import {page, pageEjdw,} from "@/services/lqbz/ylzbgl";
import YlzbgyzModal from "./components";
import ModalImport from "./components/import";
import moment from 'moment' ;
import ExportModal from "./components/ExportModal";

const Ylzbgl = () =>{
  const actionRef = useRef() ;
  const [modalVisible,handleModalVisible] = useState(false) ;
  const [zbzId,handleZbzId] = useState() ;
  const [formValues, handleFormValues] = useState({});
  const [modalImportVisible, handleModalImportVisible] = useState(false);
  const [year,handleYear] = useState(moment(new Date()).format("yyyy"))
  const [exportModalVisible, handleExportModalVisible] = useState(false);

  const handleyear = (v) =>{
    handleYear(moment(v).format("yyyy"))
  }


  const columns = [
    { title: '年度', dataIndex: 'year',
      initialValue:moment(new Date(),"yyyy"),
      renderFormItem: (item, {type, defaultRender
        , ...rest}) => {
        return <DatePicker  picker="year"  format="yyyy" onChange={handleyear} allowClear={false} inputReadOnly/>;
      }},
    { title: '摘要', dataIndex: 'numberplate'},
    { title: '年度分配', dataIndex: 'niandufenpei',hideInSearch : true },
    { title: '战区增拨', dataIndex: 'zengbo' ,hideInSearch : true},
    { title: '转供支出', dataIndex: 'zhuangong' ,hideInSearch : true},
    { title: '充卡支出',dataIndex: 'chongkazc', hideInSearch : true,},
    { title: '结存', dataIndex: 'unitinformation' ,hideInSearch : true},
    { title: '备注', dataIndex: 'remark' ,hideInSearch : true},
    {
      title: '操作',
      dataIndex: 'option',
      valueType: 'option',
      render: (_, record) => {
        if (record.numberplate !=='合计') {
          return <a
            onClick={() => {
              handleZbzId(record.id);
              handleModalVisible(true);
              handleFormValues(record);
            }}
          > 修改 </a>
        }
      }
    },
    ]


  const handleShow = () =>{
    handleZbzId(null);
    handleModalVisible(true);
    handleFormValues({});
  }


  return (
    <PageHeaderWrapper>
      <ProTable
        headerTitle="油料指标数据列表"
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
        request={(params) => {

          return pageEjdw({...params,...{year}});
        }}
        columns={columns}
      />
      {modalVisible ? (
        <YlzbgyzModal
          onSubmit={() => {
            handleModalVisible(false);
            if (actionRef.current) actionRef.current.reload();
          }}
          onCancel={() => {
            handleModalVisible(false);
          }}
          modalVisible={modalVisible}
          zbzId={zbzId}
          values={formValues}
        />
      ) : null}

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
