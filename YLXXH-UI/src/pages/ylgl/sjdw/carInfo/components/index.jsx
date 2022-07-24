import React,{ useState } from 'react' ;
import { Modal, Form, Input, Upload, message,Select  } from 'antd';
import {save,update} from "@/services/carInfo";

const FormItem = Form.Item;

const formItemLayout = {
  labelCol: { span: 5 },
  wrapperCol: { span: 19 },
};

const CarInfoModalEjdw = (props) =>{
  const { modalVisible, onSubmit, onCancel,carId ,values } = props;

  // console.log(statement) ;
  const [form] = Form.useForm();

  const [loading, handleLoading] = useState(false);

  const okHandle = async () => {
    handleLoading(true);
    const id = carId ;
    const fieldsValue = await form.validateFields();
    let data = null;
    // eslint-disable-next-line no-const-assign
    if (id){ data =await update({ ...fieldsValue, ...{id} })}
    else{data = await save({ ...fieldsValue, ...{id} })};

    if (data.result){
      message.success('操作成功 !');
      onSubmit();
    } else {
      message.error(data.message);
    }

    handleLoading(false);
  }


  return (
    <Modal
      confirmLoading={loading}
      destroyOnClose
      title={carId ? '修改车辆信息' : '新建车辆信息'}
      visible={modalVisible}
      onOk={okHandle}
      onCancel={onCancel}
    >
      <Form
        initialValues={{
          numberplate: values.numberplate
          , brandmodel: values.brandmodel
          , framenumber: values.framenumber
          , enginenumber: values.enginenumber
          , carryovermileage: values.carryovermileage
          , oilcardno: values.oilcardno}}
        form={form}
      >
        <FormItem
          {...formItemLayout}
          label="车牌号"
          name="numberplate"
          rules={[{ required: true, message: '请填写车牌号!' }]}
        >
          <Input></Input>
        </FormItem>
        <FormItem
          {...formItemLayout}
          label="厂牌型号"
          name="brandmodel"
          rules={[{ required: true, message: '请填写厂牌型号!' }]}
        >
          <Input></Input>
        </FormItem>

        <FormItem
          {...formItemLayout}
          label="车架号码"
          name="framenumber"
          rules={[{ required: true, message: '请填写车架号码!' }]}
        >
          <Input></Input>
        </FormItem>

        <FormItem
          {...formItemLayout}
          label="发动机号"
          name="enginenumber"
          rules={[{ required: true, message: '请填写发动机号!' }]}
        >
          <Input></Input>
        </FormItem>
        <FormItem
          {...formItemLayout}
          label="结转的行驶里程"
          name="carryovermileage"
          rules={[{ required: true, message: '请填写结转的行驶里程!' }]}

        >
          <Input></Input>
        </FormItem>

        <FormItem
          {...formItemLayout}
          label="加油卡号"
          name="oilcardno"
          rules={[{ required: true, message: '请填写加油卡号!' }]}
        >
          <Input></Input>
        </FormItem>
      </Form>
    </Modal>
  )
}

export default CarInfoModalEjdw ;
