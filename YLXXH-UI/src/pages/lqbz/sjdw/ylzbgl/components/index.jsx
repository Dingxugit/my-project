import React, {useState} from 'react';
import {Form, Input, InputNumber, message, Modal,Select,DatePicker} from 'antd';
import {save, update,searchExists} from "@/services/lqbz/ylzbgl";
import {CarInfo} from '@/components/gy' ;
import moment from 'moment' ;

const FormItem = Form.Item;
const {Option} = Select ;

const formItemLayout = {
  labelCol: { span: 5 },
  wrapperCol: { span: 19 },
};


const YlzbgyzModal = (props) =>{
  const { modalVisible, onSubmit, onCancel,zbzId ,values,boo } = props;

  const [form] = Form.useForm();

  const [loading, handleLoading] = useState(false);
  const [carVisible,handleCarVisible] = useState(boo) ;
  const [yearV,handleYearV] = useState(moment(new Date(),"yyyy")) ;
  const [show,handleShow] = useState(false) ;

  const okHandle = async (e) => {
    handleLoading(true);
    const id = zbzId ;
    const fieldsValue = await form.validateFields();
    let data = null;

    // eslint-disable-next-line no-const-assign
    fieldsValue.year = fieldsValue.year.format("yyyy")
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

  const onSelect = (v) =>{
    if (v===1){
      handleCarVisible(false);
      if (values.type !== v) {// 由车改为单位
        searchExists({year: moment(yearV).format("yyyy"), type: v}).then((res) => {
          if (res.result) {
            handleShow(true);
            message.error("该单位本年度已录入指标账，请勿重复录入");
          } else {
            handleShow(false);
          }
        })
      }
    }else{
      handleCarVisible(true);
      if (zbzId){ // 修改
        handleShow(false) ;
      }else{
        handleShow(false) ;
      }
    }
  }


const selectDate = (v) =>{
  handleYearV(v);
}

const onChange = async (e) =>{
  // eslint-disable-next-line no-undef
  if (values.numberplate !== e) {
    searchExists({year: moment(yearV).format("yyyy"), numberplate: e}).then((res) => {
      if (res.result) {
        handleShow(true);
        message.error("该车牌号本年度已录入指标账，请勿重复录入");
      } else {
        handleShow(false);
      }
    })
  }else{
    handleShow(false);
  }
  }



  return (
    <Modal
      confirmLoading={loading}
      destroyOnClose
      title={zbzId ? '修改油料供应指标账信息' : '新建油料供应指标账信息'}
      visible={modalVisible}
      onOk={okHandle}
      okButtonProps={{ hidden: show }}
      onCancel={onCancel}
    >
      <Form
        initialValues={{
          year: values.year ? moment(values.year,"yyyy") : moment(new Date(),"yyyy")
          ,type: values.type
          , numberplate: values.numberplate
          , niandufenpei: values.niandufenpei
          , zengbo: values.zengbo
          , zhuangong: values.zhuangong
          , zhichuleixing92:  values.zhichuleixing92
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
          {/*<InputNumber style={{width: '80%'}} onChange={selectDate}></InputNumber>*/}
          <DatePicker picker="year" onChange={selectDate} defaultValue={moment(new Date(),"yyyy")} format="yyyy" style={{width: '80%'}} />
        </FormItem>
        <FormItem
          {...formItemLayout}
          label="录入类型"
          name="type"
          rules={[{ required: true, message: '请选择录入类型!' }]}
        >
          <Select onSelect={onSelect} style={{width: '80%'}}>
            <Option key={1} value={1}>单位</Option>
            <Option key={2} value={2} >车牌号</Option>
          </Select>
        </FormItem>

        {carVisible? <FormItem
          {...formItemLayout}
          label="车号"
          name="numberplate"
          rules={[{ required: true, message: '请填写车号!' }]}
        >
          <CarInfo onChange={onChange}/>
        </FormItem> : null}

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
          label="增拨"
          name="zengbo"
          rules={[{ required: true, message: '请填写增拨!' }]}
        >
          <InputNumber style={{width: '80%'}}></InputNumber>
        </FormItem>
        <FormItem
          {...formItemLayout}
          label="转供"
          name="zhuangong"
          rules={[{ required: true, message: '请填写转供!' }]}

        >
          <InputNumber style={{width: '80%'}}></InputNumber>
        </FormItem>

      </Form>
    </Modal>
  )
}

export default YlzbgyzModal ;
