import React, {useRef, useState} from 'react';
import {page} from "@/services/lqbz/jykgl/jykxxtj";
import JykxxtjModal from "./components";
import {PageHeaderWrapper} from '@ant-design/pro-layout';
import ProTable from '@ant-design/pro-table';
import {Button} from 'antd';
import ExportJsonExcel from "js-export-excel"
import {getJykxxtjList} from "@/services/lqbz/jykgl/jykxxtj";
import PicModal from "./components/PicModal";
import {getAccessToken} from "@/utils/token";
import ExportModal from "./components/ExportModal";

const Jykxxtj = () =>{
  const actionRef = useRef() ;
  const [modalVisible,handleModalVisible] = useState(false) ;
  const [tjId,handleTjId] = useState() ;
  const [formValues, handleFormValues] = useState({});
  const [picModalVisible,handlePicModalVisible] = useState(false) ;
  const [carNum,handleCarNum] = useState('') ;
  const [exportModalVisible, handleExportModalVisible] = useState(false);
  const dept = JSON.parse(sessionStorage.getItem('DEPT')) ;

  const columns = [
    { title: '加油卡卡号', dataIndex: 'fuelcardnumber'},
    { title: '车牌号', dataIndex: 'numberplate',
      // render: (text, record) => (
      //     <a
      //       onClick={() => {
      //         handlePicModalVisible(true) ;
      //         handleCarNum(record.numberplate)
      //       }}
      //     > {text} </a>
      // )
    },
    { title: '厂牌型号', dataIndex: 'brandmodel',hideInSearch : true,},
    { title: '发动机号', dataIndex: 'enginenumber' ,hideInSearch : true},
    { title: '车架号', dataIndex: 'framenumber' ,hideInSearch : true},
    { title: '剩余指标', dataIndex: 'shengyuzhibiao' ,hideInSearch : true},
    { title: '确认签字', dataIndex: 'querequanzu' ,hideInSearch : true},
    { title: '填报时间', dataIndex: 'tianbaoshijian' ,hideInSearch : true},
    {
      title: '操作',
      dataIndex: 'option',
      valueType: 'option',
      render: (_, record) => (
        <>
        <a
          onClick={() => {
            handleTjId(record.id);
            handleModalVisible(true);
            handleFormValues(record);
          }}
        > 修改 </a>
          </>
      )
    },
  ]

  const handleShow = () =>{
    handleTjId(null);
    handleModalVisible(true);
    handleFormValues({});
  }


  const sheetHeader = [
    '编制单位','加油卡卡号','车牌号', '厂牌型号', '发动机号', '车架号', '剩余指标', '确认签字','填报时间',
  ]

  /**
   * 导出数据
   */
  const handleExport = async () => {
    const data = await getJykxxtjList() ;
    const list = Array.isArray(data.result) ? data.result : [];

    const sheetDataList = [];
    list.forEach( v => {
      sheetDataList.push({
        ...v,
      })
    })

    const option = {};
    option.fileName = `${dept.name}加油卡信息统计`;
    option.datas = [
      {
        sheetData: sheetDataList,
        sheetFilter: [
          'bianzhidanwei', 'fuelcardnumber','numberplate', 'brandmodel', 'enginenumber', 'framenumber',
          'shengyuzhibiao', 'querequanzu','tianbaoshijian',
        ],
        sheetHeader,
      },
    ];
    const toExcel = new ExportJsonExcel(option);
    toExcel.saveExcel();
  }

  const handleExportPic = async () =>{
    fetch('/zxHealth/t/lqbz/file/exportPic', { // downloadFiles 接口请求地址
      method: 'POST',
      headers: new Headers({
        'Content-Type': 'application/zip',
        'Authorization': `Bearer ${getAccessToken()}`,// 设置header 获取token
      })
      ,body:JSON.stringify({ fileName: "车辆图片.zip"})
    }).then((response) => {
      response.blob().then(blob => {
        const blobUrl = window.URL.createObjectURL(blob);
        const aElement = document.getElementById('downloadMzDiv'); // 获取a标签元素
        // eslint-disable-next-line no-undef
        const filename = `车辆图片.zip`;// 设置文件名称
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
        headerTitle="加油卡信息统计表"
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
            onClick={() => { handleExportModalVisible(true)}}
          >
            标准导出
          </Button>,
          <a id="downloadzbzDiv" style={{display: 'none'}}>mz</a>,
          <Button
            // icon={<VerticalAlignBottomOutlined />}
            onClick={() => { handleExportPic(); }}
          >
            图片下载
          </Button>,
          <a id="downloadMzDiv" style={{display: 'none'}}>mz</a>,

        ]}
        request={(params) => page({...params,})}
        columns={columns}
      />
      {modalVisible ? (
        <JykxxtjModal
          onSubmit={() => {
            handleModalVisible(false);
            if (actionRef.current) actionRef.current.reload();
          }}
          onCancel={() => {
            handleModalVisible(false);
          }}
          modalVisible={modalVisible}
          tjId={tjId}
          values={formValues}
        />
      ) : null }

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
