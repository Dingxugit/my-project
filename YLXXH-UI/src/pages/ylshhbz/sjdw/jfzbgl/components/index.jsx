import React, {useState} from 'react';
import {Form, Input, InputNumber, message, Modal,Select,DatePicker} from 'antd';
import {save, update} from "@/services/ylshh/jfzb";
import {CarInfo} from '@/components/gy' ;
import {searchExists} from "@/services/ylshh/jfzb";
import moment from 'moment' ;

const FormItem = Form.Item;
const {Option} = Select ;

const formItemLayout = {
  labelCol: { span: 5 },
  wrapperCol: { span: 19 },
};

const JfbzModal = (props) =>{
  const { modalVisible, onSubmit, onCancel,zbzId ,values,boo } = props;

  // console.log(statement) ;
  const [form] = Form.useForm();

  const [loading, handleLoading] = useState(false);
  const [carVisible,handleCarVisible] = useState(boo) ;
  const [show,handleShow] = useState(false) ;
  const [csz,handleCsz] = useState(values.numberplate) ;
  const [yearV,handleYearV] = useState(moment(new Date(),"yyyy")) ;


  const okHandle = async () => {
    handleLoading(true);
    const id = zbzId ;
    const fieldsValue = await form.validateFields();
    let data = null;
    fieldsValue.year = fieldsValue.year.format("yyyy")
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

  const onSelect = (v) =>{
    if (v===1){
      handleCarVisible(false);
      if (values.type !== v) {
        searchExists({year: moment(yearV).format("yyyy"), type: v}).then((res) => {
          if (res.result) {
            handleShow(true);
            message.error("该单位本年度已录入指标账，请勿重复录入");
          } else {
            handleShow(false);
          }
        })
      }else{
        handleShow(false) ;
      }
    }else{
      handleCarVisible(true);
      handleShow(false) ;
    }
  }


  const onChange = async (e) =>{
    // eslint-disable-next-line no-undef
    if (values.numberplate !== e){
      searchExists({year:moment(yearV).format("yyyy"),numberplate: e}).then((res) =>{
        if (res.result){
          handleShow(true) ;
          message.error("该车牌号本年度已录入指标账，请勿重复录入");
        }else{
          handleShow(false) ;
        }
      })
    }else{
      handleShow(false) ;
    }

  }

  const selectDate = (v) =>{
    handleYearV(v);
  }

  return (
    <Modal
      confirmLoading={loading}
      destroyOnClose
      title={zbzId ? '修改油料经费指标账信息' : '新建油料经费指标账信息'}
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
          <InputNumber style={{width: '80%'}} ></InputNumber>
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
