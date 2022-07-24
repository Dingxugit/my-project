import React, {useState} from 'react';
import {Form, Input, InputNumber, message, Modal,Select,DatePicker} from 'antd';
import {ejdwSave, ejdwUpdate} from "@/services/lqbz/ylzbgl";
import {DeptInfo} from '@/components/gy' ;
import moment from 'moment' ;

const FormItem = Form.Item;
const {Option} = Select ;

const formItemLayout = {
  labelCol: { span: 5 },
  wrapperCol: { span: 19 },
};

const YlzbgyzModal = (props) =>{
  const { modalVisible, onSubmit, onCancel,zbzId ,values, } = props;

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
      title={zbzId ? '修改油料供应指标账信息' : '新建油料供应指标账信息'}
      visible={modalVisible}
      onOk={okHandle}
      onCancel={onCancel}
    >
      <Form
        initialValues={{
          year: values.year ? moment(values.year,"yyyy") : moment(new Date(),"yyyy")
          , numberplate: values.numberplate
          , niandufenpei: values.niandufenpei
          , zengbo: values.zengbo
          , zhuangong: values.zhuangong
          , chongkazc: values.chongkazc
          , unitinformation: values.unitinformation
          , remark: values.remark
        }}
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
          label="战区增拨"
          name="zengbo"
          rules={[{ required: true, message: '请填写增拨!' }]}
        >
          <InputNumber style={{width: '80%'}}></InputNumber>
        </FormItem>
        <FormItem
          {...formItemLayout}
          label="转供支出"
          name="zhuangong"
          rules={[{ required: true, message: '请填写转供!' }]}

        >
          <InputNumber style={{width: '80%'}}></InputNumber>
        </FormItem>
        <FormItem
            {...formItemLayout}
            label="充卡支出"
            name="chongkazc"
            rules={[{ required: true, message: '请填写充卡支出!' }]}
          >
            <InputNumber style={{width: '80%'}}></InputNumber>
          </FormItem>
        <FormItem
          {...formItemLayout}
          label="备注"
          name="remark"
        >
          <Input style={{width: '80%'}}></Input>
        </FormItem>

      </Form>
    </Modal>
  )
}

export default YlzbgyzModal ;
