import React, {useState,useEffect} from 'react';
import {Form, Input, message, Modal,DatePicker,Select} from 'antd';
import {save, update} from "@/services/lqbz/jykgl/jykrcgl";
import {CarInfo} from '@/components/gy';
import {getCarInfoBynum} from '@/services/carInfo'

import moment from 'moment';

const {Option} = Select ;
const FormItem = Form.Item;

const formItemLayout = {
  labelCol: { span: 5 },
  wrapperCol: { span: 19 },
};
const JykrcglModal = (props) =>{
  const dateFormat = 'YYYY-MM-DD';
  const { modalVisible, onSubmit, onCancel,djId ,values } = props;
  const [carInfo,handleCarInfo] = useState(null) ;


  // console.log(statement) ;
  const [form] = Form.useForm();

  const [loading, handleLoading] = useState(false);

  const okHandle = async () => {
    handleLoading(true);
    const id = djId ;
    const fieldsValue = await form.validateFields();
    let data = null;
    fieldsValue.lingqusj = fieldsValue.lingqusj.format(dateFormat) ;
    fieldsValue.guihuanshijian = fieldsValue.guihuanshijian.format(dateFormat) ;
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

  const getCarInfo = (e) =>{
    getCarInfoBynum({numberplate: e}).then((res) =>{
        if (res.result){
          form.setFieldsValue({
            fuelcardnumber: res.result.oilcardno,
          });
        }
    })
  }

  return (
    <Modal
      confirmLoading={loading}
      destroyOnClose
      title={djId ? '修改加油卡登记信息' : '新建加油卡登记信息'}
      visible={modalVisible}
      onOk={okHandle}
      onCancel={onCancel}
    >
      <Form
        initialValues={{
          fuelcardnumber: values.fuelcardnumber
          ,numberplate: values.numberplate
          , youpin: values.youpin
          , lingkashiyou: values.lingkashiyou
          , lingqusj: values.lingqusj ? moment(values.lingqusj) : moment(new Date())
          , kaneiyouliang: values.kaneiyouliang
          , guihuanshijian: values.guihuanshijian ? moment(values.guihuanshijian) : moment(new Date())
          , kaneiyue: values.kaneiyue
          , jaishiyuanqianzi: values.jaishiyuanqianzi}}
        form={form}
      >
        <FormItem
          {...formItemLayout}
          label="加油卡卡号"
          name="fuelcardnumber"
        >
          <Input style={{width: '80%'}} disabled={true}></Input>
        </FormItem>

      <FormItem
          {...formItemLayout}
          label="车牌号"
          name="numberplate"
        >
          <CarInfo onChange={getCarInfo}/>
        </FormItem>

        <FormItem
          {...formItemLayout}
          label="油品"
          name="youpin"
        >
          <Select style={{width: '80%'}}>
            <Option value={"92#"}>92#</Option>
            <Option value={"95#"}>95#</Option>
            <Option value={"35#"}>35#</Option>
          </Select>
        </FormItem>

        <FormItem
          {...formItemLayout}
          label="领卡事由"
          name="lingkashiyou"
        >
          <Input style={{width: '80%'}} ></Input>
        </FormItem>
        <FormItem
          {...formItemLayout}
          label="领取时间"
          name="lingqusj"

        >
          <DatePicker style={{width: '80%'}} />
        </FormItem>

        <FormItem
          {...formItemLayout}
          label="卡内油量L"
          name="kaneiyouliang"
        >
          <Input style={{width: '80%'}} ></Input>
        </FormItem>
        <FormItem
          {...formItemLayout}
          label="归还时间"
          name="guihuanshijian"
        >
          <DatePicker style={{width: '80%'}} />
        </FormItem>
        <FormItem
          {...formItemLayout}
          label="卡内余额"
          name="kaneiyue"
        >
          <Input style={{width: '80%'}} ></Input>
        </FormItem>
        <FormItem
          {...formItemLayout}
          label="驾驶员签字"
          name="jaishiyuanqianzi"
        >
          <Input style={{width: '80%'}} ></Input>
        </FormItem>
      </Form>
    </Modal>
  )
}

export default JykrcglModal ;
