import React, {useState} from 'react';
import {Form, Input, InputNumber, message, Modal,Select,DatePicker} from 'antd';
import {save, update,searchExists} from "@/services/swy/ylzb";
import moment from 'moment';

const FormItem = Form.Item;
const {Option} = Select ;

const formItemLayout = {
  labelCol: { span: 5 },
  wrapperCol: { span: 17 },
};

const SwylzModal = (props) =>{
  const { modalVisible, onSubmit, onCancel,zbzId ,values, } = props;
  const dateFormat = 'YYYY-MM-DD';
  const [form] = Form.useForm();

  const [loading, handleLoading] = useState(false);
  const [show,handleShow] = useState(false) ;


  const okHandle = async () => {
    handleLoading(true);
    const id = zbzId ;
    const fieldsValue = await form.validateFields();
    let data = null;
    fieldsValue.riqi = fieldsValue.riqi.format(dateFormat) ;
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

    if (values.youpin !== form.getFieldValue("youpin")){
      searchExists({youpin: form.getFieldValue("youpin")}).then((res) =>{
        if (res.result){
          handleShow(true) ;
          message.error("该油品已存在，请勿重复录入");
        }else{
          handleShow(false) ;
          if (zbzId){
            handleShow(false) ;
          }
        }
      })
    }
  }



  return (
    <Modal
      confirmLoading={loading}
      destroyOnClose
      title={zbzId ? '修改价拨/实物油料账信息' : '新建价拨/实物油料账信息'}
      visible={modalVisible}
      onOk={okHandle}
      okButtonProps={{ hidden: show }}
      onCancel={onCancel}
    >
      <Form
        initialValues={{
          riqi: values.riqi ? moment(values.riqi) : moment(new Date()),
          pingzhenghao: values.pingzhenghao
          , youliaozhibiaoshouru: values.youliaozhibiaoshouru
          , youpin: values.youpin
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

          label="摘要"
          name="zhaiyao"
        >
          <Input style={{width: '80%'}} ></Input>
        </FormItem>

        <FormItem
          {...formItemLayout}
          label="收入"
          name="youliaozhibiaoshouru"
          rules={[{ required: true, message: '请填写收入!' }]}
        >
          <InputNumber style={{width: '80%'}}></InputNumber>
        </FormItem>

        <FormItem
          {...formItemLayout}
          label="油品"
          name="youpin"
          rules={[{ required: true, message: '请填写油品!' }]}

        >
          <Select  style={{width: '80%'}} onSelect={onSelect}>
            <Option value={"92#"}>92#</Option>
            <Option value={"95#"}>95#</Option>
            <Option value={"-35#"}>-35#</Option>
          </Select>
        </FormItem>

      </Form>
    </Modal>
  )
}

export default SwylzModal ;
