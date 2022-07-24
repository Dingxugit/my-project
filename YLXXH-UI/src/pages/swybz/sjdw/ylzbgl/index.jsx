import React, {useRef, useState} from 'react';
import {PageHeaderWrapper} from '@ant-design/pro-layout';
import ProTable from '@ant-design/pro-table';
import {Button} from 'antd';
import {page,getDeptZbzList} from "@/services/swy/ylzb";
import ExportJsonExcel from "js-export-excel"
import SwylzModal from './components' ;
import {getAccessToken} from "@/utils/token";

const Ylzbgl = () =>{
  const actionRef = useRef() ;
  const [modalVisible,handleModalVisible] = useState(false) ;
  const [zbzId,handleZbzId] = useState() ;
  const [formValues, handleFormValues] = useState({});
  const dept = JSON.parse(sessionStorage.getItem('DEPT')) ;

  const columns = [
    { title: '用油单位', dataIndex: 'yongyoudanwei',hideInSearch : true},
    { title: '日期', dataIndex: 'riqi'},
    { title: '凭证号', dataIndex: 'pingzhenghao', },
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
          }}
        > 修改 </a>
      )
    },
  ]

  const sheetHeader = [
    '用油单位','日期', '凭证号', '摘要', '油品', '油料指标收入','油料指标支出','油料指标结存',
  ]

  /**
   * 导出数据
   */
  const handleExport = async () => {
    const data = await getDeptZbzList() ;
    const list = Array.isArray(data.result) ? data.result : [];

    const sheetDataList = [];
    list.forEach( v => {
      sheetDataList.push({
        ...v,
      })
    })

    const option = {};
    option.fileName = `${dept.name}价拨/实物油料账`;
    option.datas = [
      {
        sheetData: sheetDataList,
        sheetFilter: [
          'yongyoudanwei','riqi', 'pingzhenghao', 'zhaiyao', 'youpin', 'youliaozhibiaoshouru', 'youliaozhibiaozhichu','youliaozhibiaojeicun',
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
    fetch('/zxHealth/t/swy/file/exportSwygl', { // downloadFiles 接口请求地址
      method: 'POST',
      headers: new Headers({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${getAccessToken()}`,// 设置header 获取token
      })
      ,body:JSON.stringify({ "yongyoudanwei": dept.name})
    }).then((response) => {
      response.blob().then(blob => {
        const blobUrl = window.URL.createObjectURL(blob);
        const aElement = document.getElementById('downloadzbzDiv'); // 获取a标签元素
        // eslint-disable-next-line no-undef
        const filename = `${dept.name}实物油管理.xls`;// 设置文件名称
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
        headerTitle="价拨/实物油料账"
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
            onClick={() => { handleExportBz(); }}
          >
            标准导出
          </Button>,
          <a id="downloadzbzDiv" style={{display: 'none'}}>mz</a>,
        ]}
        request={(params) => page({...params,})}
        columns={columns}
      />
      {modalVisible ? (
        <SwylzModal
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
    </PageHeaderWrapper>
  )
}

export default Ylzbgl ;
