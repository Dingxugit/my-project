import React, {useState} from 'react';
import {Form, Input, InputNumber, message, Modal,Select,DatePicker} from 'antd';
import {ejdwSave, ejdwUpdate} from "@/services/ylshh/jfzb";
import {DeptInfo} from '@/components/gy' ;
import moment from 'moment' ;

const FormItem = Form.Item;
const {Option} = Select ;

const formItemLayout = {
  labelCol: { span: 5 },
  wrapperCol: { span: 19 },
};

const JfbzModal = (props) =>{
  const { modalVisible, onSubmit, onCancel,zbzId ,values, } = props;

  // console.log(statement) ;
  const [form] = Form.useForm();

  const [loading, handleLoading] = useState(false);
  const [yearV,handleYearV] = useState(moment(new Date(),"yyyy")) ;

  const okHandle = async () => {
    handleLoading(true);
    const id = zbzId ;
    const fieldsValue = await form.validateFields();
    let data = null;
    fieldsValue.year = fieldsValue.year.format("yyyy")
    // eslint-disable-next-line no-const-assign
    if (id){ data =await ejdwUpdate({ ...fieldsValue, ...{id} })}
    else{data = await ejdwSave({ ...fieldsValue, ...{id} })};

    if (data.result){
      message.success('操作成功 !');
      onSubmit();
    } else {
      message.error(data.message);
    }

    handleLoading(false);
  }

  const selectDate = (v) =>{
    handleYearV(v);
  }

  return (
    <Modal
      confirmLoading={loading}
      destroyOnClose
      title={zbzId ? '修改经费指标账信息' : '新建经费指标账信息'}
      visible={modalVisible}
      onOk={okHandle}
      onCancel={onCancel}
    >
      <Form
        initialValues={{
          year: values.year ? moment(values.year,"yyyy") : moment(new Date(),"yyyy")
          ,type: values.type
          , numberplate: values.numberplate
          , niandufenpei: values.niandufenpei
          , zhukajingfei: values.zhukajingfei
          , zhichuleixing92: values.zhichuleixing92
          , zhichuleixing95: values.zhichuleixing95
          , zhichuleixing35: values.zhichuleixing35
          , unitinformation: values.unitinformation}}
        form={form}
      >
        <FormItem
          {...formItemLayout}
          label="年度"
          name="year"
          rules={[{ required: true, message: '请填写年度!' }]}
        >
          <DatePicker picker="year" onChange={selectDate} defaultValue={moment(new Date(),"yyyy")} format="yyyy" style={{width: '80%'}} />
        </FormItem>

       <FormItem
          {...formItemLayout}
          label="摘要"
          name="numberplate"
          rules={[{ required: true, message: '请填写摘要!' }]}
        >
          <DeptInfo />
        </FormItem>

        <FormItem
          {...formItemLayout}
          label="年度分配"
          name="niandufenpei"
          rules={[{ required: true, message: '请填写年度分配!' }]}
        >
          <InputNumber style={{width: '80%'}}></InputNumber>
        </FormItem>

        <FormItem
          {...formItemLayout}
          label="主卡经费"
          name="zhukajingfei"
          rules={[{ required: true, message: '请填写主卡经费!' }]}
        >
          <InputNumber style={{width: '80%'}}></InputNumber>
        </FormItem>

      </Form>
    </Modal>
  )
}

export default JfbzModal ;
