import React, {useRef, useState} from 'react';
import {PageHeaderWrapper} from '@ant-design/pro-layout';
import ProTable from '@ant-design/pro-table';
import {Button} from 'antd';
import CarInfoModalEjdw from "./components";
import {page} from '@/services/carInfo'
import ModalImport from "./components/import";

const CarInfoEjdw = () =>{

  const actionRef = useRef();
  const [modalVisible,handleModalVisible] = useState(false) ;
  const [carId,handleCarId] = useState() ;
  const [formValues, handleFormValues] = useState({});
  const [modalImportVisible, handleModalImportVisible] = useState(false);

  const columns = [
    { title: '车牌号', dataIndex: 'numberplate'},
    { title: '厂牌型号', dataIndex: 'brandmodel',hideInSearch : true },
    { title: '车架号码', dataIndex: 'framenumber' },
    { title: '发动机号', dataIndex: 'enginenumber' ,hideInSearch : true},
    { title: '结转的行驶里程', dataIndex: 'carryovermileage' ,hideInSearch : true,},
    { title: '所属单位', dataIndex: 'unitName' },
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
            pagination={{pageSize:10}}
            scroll={{y: 'max-content'}}
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
              </Button>

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
          carId={carId}
          modalVisible={modalVisible}
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
    </PageHeaderWrapper>
  )
}

export default CarInfoEjdw ;
