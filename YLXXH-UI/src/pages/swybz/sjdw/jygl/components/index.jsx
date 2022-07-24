import React, {useState} from 'react';

import {DatePicker, Form, Input, InputNumber, message, Modal, Select} from 'antd';
import {save, update} from "@/services/swy/dj";
import moment from 'moment';
import {CarInfo} from '@/components/gy' ;
import {getCarInfoBynum} from "@/services/carInfo";


const FormItem = Form.Item;
const {Option}= Select ;

const formItemLayout = {
  labelCol: { span: 6 },
  wrapperCol: { span: 16 },
};

const JyglModal = (props) =>{
  const dateFormat = 'YYYY-MM-DD';

  const { modalVisible, onSubmit, onCancel,jyId ,values ,defValue} = props;

  // console.log(statement) ;
  const [form] = Form.useForm();

  const [loading, handleLoading] = useState(false);

  const okHandle = async () => {
    handleLoading(true);
    const id = jyId ;
    const fieldsValue = await form.validateFields();
    let data = null;
    // eslint-disable-next-line no-const-assign
    fieldsValue.riqi = fieldsValue.riqi.format(dateFormat) ;
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

  const getCarInfo = (e) =>{
    if (e !== undefined){
      getCarInfoBynum({numberplate: e}).then((res)=>{
        form.setFieldsValue({
          fadongjihao: res.result.enginenumber,
          chejiahao: res.result.framenumber
        });

      })
    }
  }



  return (
    <Modal
      confirmLoading={loading}
      destroyOnClose
      title={jyId ? '修改价拨/实物油加注登记信息' : '新建价拨/实物油加注登记信息'}
      visible={modalVisible}
      onOk={okHandle}
      onCancel={onCancel}
    >

      <Form
        initialValues={{
          riqi: values.riqi ? moment(values.riqi) : moment(new Date())
          ,yongyoudanwei: values.yongyoudanwei
          , pingzhenghao: values.pingzhenghao
          , zhuangbeihao: values.zhuangbeihao
          , fadongjihao: values.fadongjihao
          , chejiahao:  values.chejiahao
          , youpin: values.youpin
          , youliaozhibiaojeicun: values.youliaozhibiaojeicun
          , sijiname: values.sijiname
          , midu: values.midu
          , jiayouyuanname: values.jiayouyuanname

        }}
        form={form}
      >
        <FormItem
          {...formItemLayout}
          label="日期"
          name="riqi"
          rules={[{ required: true, message: '请填写日期!' }]}
        >
          <DatePicker style={{width: '80%'}}/>
        </FormItem>

        <FormItem
          {...formItemLayout}
          label="凭证号"
          name="pingzhenghao"
          rules={[{ required: true, message: '请填写凭证号!' }]}
        >
          <Input style={{width: '80%'}}></Input>
        </FormItem>

        <FormItem
          {...formItemLayout}
          label="装备号"
          name="zhuangbeihao"
          rules={[{ required: true, message: '请填写装备号!' }]}
        >
          {/*<Input style={{width: '80%'}}></Input>*/}
          <CarInfo onChange={getCarInfo}/>
        </FormItem>

        <FormItem
          {...formItemLayout}
          label="发动机号"
          name="fadongjihao"
          rules={[{ required: true, message: '请填写发动机号!' }]}
        >
          <Input style={{width: '80%'}}></Input>
        </FormItem>
        <FormItem
          {...formItemLayout}
          label="车架号"
          name="chejiahao"
          rules={[{ required: true, message: '请填写车架号!' }]}

        >
          <Input style={{width: '80%'}}></Input>
        </FormItem>

        <FormItem
          {...formItemLayout}
          label="油品"
          name="youpin"
          rules={[{ required: true, message: '请填写油品!' }]}

        >
          <Select value={defValue} style={{width: '80%'}}>
            <Option value={"92#"}>92#</Option>
            <Option value={"95#"}>95#</Option>
            <Option value={"-35#"}>-35#</Option>
          </Select>
        </FormItem>

        <FormItem
          {...formItemLayout}
          label="加油量L"
          name="youliaozhibiaojeicun"
          rules={[{ required: true, message: '请填写加油量!' }]}

        >
          <InputNumber style={{width: '80%'}}></InputNumber>
        </FormItem>
        <FormItem
          {...formItemLayout}
          label="密度"
          name="midu"
          rules={[{ required: true, message: '请填写密度!' }]}

        >
          <InputNumber style={{width: '80%'}}></InputNumber>
        </FormItem>
        <FormItem
          {...formItemLayout}
          label="司机姓名"
          name="sijiname"
        >
          <Input style={{width: '80%'}}></Input>
        </FormItem>
        <FormItem
            {...formItemLayout}
            label="加油员签字"
            name="jiayouyuanname"
          >
            <Input style={{width: '80%'}}></Input>
        </FormItem>

      </Form>
    </Modal>
  )
}

export default JyglModal ;
