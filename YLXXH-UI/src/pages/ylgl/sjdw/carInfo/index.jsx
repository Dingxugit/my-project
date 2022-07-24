import React, {useRef, useState} from 'react';
import {PageHeaderWrapper} from '@ant-design/pro-layout';
import ProTable from '@ant-design/pro-table';
import {Button} from 'antd';
import CarInfoModalEjdw from "./components";
import {page} from '@/services/carInfo'
import ExportJsonExcel from "js-export-excel"
import {getCarList} from "@/services/carInfo";

const CarInfoEjdw = () =>{

  const actionRef = useRef();
  const [modalVisible,handleModalVisible] = useState(false) ;
  const [carId,handleCarId] = useState() ;
  const [formValues, handleFormValues] = useState({});
  const columns = [
    { title: '车牌号', dataIndex: 'numberplate' },
    { title: '厂牌型号', dataIndex: 'brandmodel',hideInSearch : true },
    { title: '车架号码', dataIndex: 'framenumber' ,},
    { title: '发动机号', dataIndex: 'enginenumber' ,hideInSearch : true},
    { title: '结转的行驶里程', dataIndex: 'carryovermileage' ,hideInSearch : true,},
    { title: '所属单位', dataIndex: 'unitName' ,hideInSearch : true},
    { title: '加油卡号', dataIndex: 'oilcardno' },
    {
      title: '操作',
      dataIndex: 'option',
      valueType: 'option',
      render: (_, record) => (
        <a
          onClick={() => {
            handleCarId(record.id);
            handleModalVisible(true);
            handleFormValues(record);
          }}
        > 修改 </a>
      )
    },
  ]

  const sheetHeader = [
    '车牌号','厂牌型号', '车架号码', '发动机号', '结转的行驶里程', '所属单位', '加油卡号',
  ]

  /**
   * 导出数据
   */
  const handleExport = async () => {
    const data = await getCarList() ;
    const list = Array.isArray(data.result) ? data.result : [];

    const sheetDataList = [];
    list.forEach( v => {
      sheetDataList.push({
        ...v,
      })
    })

    const option = {};
    option.fileName = `车辆信息`;
    option.datas = [
      {
        sheetData: sheetDataList,
        sheetFilter: [
          'numberplate','brandmodel', 'framenumber', 'enginenumber', 'carryovermileage', 'unitName', 'oilcardno'
        ],
        sheetHeader,
      },
    ];
    const toExcel = new ExportJsonExcel(option);
    toExcel.saveExcel();
  }

  const handleShow = () =>{
    handleCarId(null);
    handleModalVisible(true);
    handleFormValues({});
  }
  return (
    <PageHeaderWrapper>
          <ProTable
            headerTitle="车辆信息数据列表"
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

            ]}
            request={(params) => page({...params,})}
            columns={columns}
          />
          {modalVisible ? (
            <CarInfoModalEjdw
              onSubmit={() => {
                handleModalVisible(false);
                if (actionRef.current) actionRef.current.reload();
              }}
              onCancel={() => {
                handleModalVisible(false);
              }}
              modalVisible={modalVisible}
              carId={carId}
              values={formValues}
            />
          ) : null}
    </PageHeaderWrapper>
  )
}

export default CarInfoEjdw ;
