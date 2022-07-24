import React, {useRef, useState} from 'react';
import {PageHeaderWrapper} from '@ant-design/pro-layout';
import ProTable from '@ant-design/pro-table';
import {Button,DatePicker} from 'antd';
import {page,getDeptZbz} from "@/services/ylshh/jfzb";
import JfbzModal from "./components";
import ExportJsonExcel from "js-export-excel"
import moment from 'moment' ;
import {getAccessToken} from "@/utils/token";

const Jfzbgl = () =>{
  const actionRef = useRef() ;
  const [modalVisible,handleModalVisible] = useState(false) ;
  const [zbzId,handleZbzId] = useState() ;
  const [formValues, handleFormValues] = useState({});
  const [boo,handleBoo] = useState(false) ;
  const [year,handleYear] = useState(moment(new Date()).format("yyyy"))
  const dept = JSON.parse(sessionStorage.getItem('DEPT')) ;

  const handleyear = (v) =>{
    handleYear(moment(v).format("yyyy"))
  }

  const columns = [
    { title: '年度', dataIndex: 'year',
      initialValue:moment(new Date()),
      renderFormItem: (item, {type, defaultRender
        , ...rest}) => {
        console.info(item)
        return <DatePicker  picker="year"  format="yyyy" onChange={handleyear} allowClear={false} inputReadOnly/>;
      }
    },
    { title: '车号', dataIndex: 'numberplate'},
    { title: '年度分配', dataIndex: 'niandufenpei',hideInSearch : true },
    { title: '主卡经费', dataIndex: 'zhukajingfei' ,hideInSearch : true},
    { title: '副卡消耗', hideInSearch : true,children:[{
        title: '92#', dataIndex: 'zhichuleixing92',
      },{
        title: '95#', dataIndex: 'zhichuleixing95',
      },{
        title: '-35#', dataIndex: 'zhichuleixing35',
      }
      ]},
    { title: '结存', dataIndex: 'unitinformation' ,hideInSearch : true},
    {
      title: '操作',
      dataIndex: 'option',
      valueType: 'option',
      render: (_, record) => (
        <a
          onClick={() => {
            handleZbzId(record.id);
            handleModalVisible(true);
            handleFormValues(record);
            if (record.type===2){handleBoo(true)}else {handleBoo(false)}
          }}
        > 修改 </a>
      )
    },
  ]

  const sheetHeader = [
    '年度','摘要', '年度分配', '主卡经费', '92#', '95#', '-35#', '结存',
  ]

  /**
   * 导出数据
   */
  const handleExport = async () => {
    const data = await getDeptZbz({year}) ;
    const list = Array.isArray(data.result) ? data.result : [];

    const sheetDataList = [];
    list.forEach( v => {
      sheetDataList.push({
        ...v,
      })
    })

    const option = {};
    option.fileName = `${dept.name}油料经费指标账`;
    option.datas = [
      {
        sheetData: sheetDataList,
        sheetFilter: [
          'year','numberplate', 'niandufenpei', 'zhukajingfei', 'zhichuleixing92', 'zhichuleixing95','zhichuleixing35', 'unitinformation',
        ],
        sheetHeader,
      },
    ];
    const toExcel = new ExportJsonExcel(option);
    toExcel.saveExcel();
  }

  const handleShow = () =>{
    handleZbzId(null);
    handleModalVisible(true);
    handleFormValues({});
  }

  const handleExportBz = async () =>{
    fetch('/zxHealth/t/shh/file/exportYlzb', { // downloadFiles 接口请求地址
      method: 'POST',
      headers: new Headers({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${getAccessToken()}`,// 设置header 获取token
      })
      ,body:JSON.stringify( {"bianzhidanwei": dept.name,"year":year})
    }).then((response) => {
      response.blob().then(blob => {
        const blobUrl = window.URL.createObjectURL(blob);
        const aElement = document.getElementById('downloadzbzDiv'); // 获取a标签元素
        // eslint-disable-next-line no-undef
        const filename = `${dept.name}油料经费指标账.xls`;// 设置文件名称
        aElement.href = blobUrl;// 设置a标签路径
        aElement.download = filename;
        aElement.click();
        window.URL.revokeObjectURL(blobUrl);
      });
    }).catch((error) => {
      console.log('文件下载失败', error);
    });
  }

  return (
    <PageHeaderWrapper>
      <ProTable
        headerTitle="油料经费指标数据列表"
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
            onClick={() => { handleExportBz(); }}
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
      {modalVisible ? (
        <JfbzModal
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
          boo={boo}
        />
      ) : null}
    </PageHeaderWrapper>
  )
}

export default Jfzbgl ;
