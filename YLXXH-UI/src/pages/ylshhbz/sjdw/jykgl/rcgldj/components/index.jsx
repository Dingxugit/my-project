import React, {useState} from 'react';
import {Form, Input, message, Modal,DatePicker,Row,Col,Select,InputNumber} from 'antd';
import {save, update} from "@/services/ylshh/jykgl/rcgldj";
import {CarInfo} from '@/components/gy';
import moment from 'moment';
import {getCarInfoBynum} from "@/services/carInfo";

const {Option} =Select ;
const FormItem = Form.Item;

const formItemLayout = {
  labelCol: { span: 5 },
  wrapperCol: { span: 19 },
};
const JykrcgldjModal = (props) =>{
  const dateFormat = 'YYYY-MM-DD';
  const { modalVisible, onSubmit, onCancel,djId ,values } = props;

  // console.log(statement) ;
  const [form] = Form.useForm();

  const [loading, handleLoading] = useState(false);

  const okHandle = async () => {
    handleLoading(true);
    const id = djId ;
    const fieldsValue = await form.validateFields();
    let data = null;
    fieldsValue.lingqushijian = fieldsValue.lingqushijian.format(dateFormat) ;
    fieldsValue.guihuanshijian = fieldsValue.guihuanshijian.format(dateFormat) ;
    fieldsValue.jiayoushijian = fieldsValue.jiayoushijian.format(dateFormat) ;
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
          jiayoukakahao: res.result.oilcardno,
          changpaixinghao: res.result.brandmodel,
        });
      }else{
        form.setFieldsValue({
          jiayoukakahao: null,
          changpaixinghao: null,
        });
      }
    })
  }

  const handleLc =() =>{
    const lccd = form.getFieldValue("shangcilichengdushu");
    const lcmd = form.getFieldValue("bencilichengdushu");
    form.setFieldsValue({
      xiingshilicheng: lcmd -lccd
    });
  }

  return (
    <Modal
      confirmLoading={loading}
      destroyOnClose
      title={djId ? '修改加油卡登记信息' : '新建加油卡登记信息'}
      visible={modalVisible}
      onOk={okHandle}
      onCancel={onCancel}
      width={1200}
    >
      <Form
        initialValues={{
          lingkashiyou: values.lingkashiyou
          , lingqushijian: values.lingqushijian ? moment(values.lingqushijian) : moment(new Date()),
          jiayoukakahao: values.jiayoukakahao
          ,numberplate: values.numberplate
          ,changpaixinghao: values.changpaixinghao
          , youpin: values.youpin
          , shangcilichengdushu: values.shangcilichengdushu
          , bencilichengdushu: values.bencilichengdushu
          , xiingshilicheng: values.xiingshilicheng
          , bencijiayouliang: values.bencijiayouliang
          , bencijiayoujine: values.bencijiayoujine
          , kaneiyue: values.kaneiyue
          , guihuanshijian: values.guihuanshijian ? moment(values.guihuanshijian) : moment(new Date())
          , jiayoushijian: values.jiayoushijian ? moment(values.jiayoushijian) : moment(new Date())
          , shifoushangjiaoxiaopiao: values.shifoushangjiaoxiaopiao
          , jaishiyuanqianzi: values.jaishiyuanqianzi}}
        form={form}
      >
        <Row>
          <Col span={12}>
            <FormItem
              {...formItemLayout}
              label="领卡事由"
              name="lingkashiyou"
              rules={[{ required: true, message: '请填写领卡事由!' }]}
            >
              <Input style={{width: '80%'}}></Input>
            </FormItem>

            <FormItem
              {...formItemLayout}
              label="领取时间"
              name="lingqushijian"
              rules={[{ required: true, message: '请填写领取时间!' }]}

            >
              <DatePicker style={{width: '80%'}}/>
            </FormItem>

            <FormItem
              {...formItemLayout}
              label="加油卡卡号"
              name="jiayoukakahao"
              rules={[{ required: true, message: '请填写加油卡卡号!' }]}
            >
              <Input style={{width: '80%'}} disabled={true}></Input>
            </FormItem>
            <FormItem
              {...formItemLayout}
              label="车牌号"
              name="numberplate"
              rules={[{ required: true, message: '请填写车牌号!' }]}
            >
              <CarInfo onChange={getCarInfo} />
            </FormItem>
            <FormItem
              {...formItemLayout}
              label="厂牌型号"
              name="changpaixinghao"
            >
              <Input style={{width: '80%'}}></Input>
            </FormItem>

            <FormItem
              {...formItemLayout}
              label="油品"
              name="youpin"
              rules={[{ required: true, message: '请填写油品!' }]}
            >
              <Select style={{width: '80%'}}>
                <Option value={"92#"}>92#</Option>
                <Option value={"95#"}>95#</Option>
                <Option value={"-35#"}>-35#</Option>
              </Select>
            </FormItem>

            <FormItem
              {...formItemLayout}
              label="上次里程读数"
              name="shangcilichengdushu"
            >
              <InputNumber style={{width: '80%'}} onChange={handleLc}></InputNumber>
            </FormItem>
            <FormItem
              {...formItemLayout}
              label="本次里程读数"
              name="bencilichengdushu"
            >
              <InputNumber style={{width: '80%'}} onChange={handleLc}></InputNumber>
            </FormItem>
          </Col>
          <Col span={12}>
            <FormItem
              {...formItemLayout}
              label="行驶里程"
              name="xiingshilicheng"
            >
              <InputNumber style={{width: '80%'}}></InputNumber>
            </FormItem>
            <FormItem
              {...formItemLayout}
              label="本次加油量L"
              name="bencijiayouliang"
            >
              <InputNumber style={{width: '80%'}}></InputNumber>
            </FormItem>
            <FormItem
              {...formItemLayout}
              label="本次加油金额(元)"
              name="bencijiayoujine"
              rules={[{ required: true, message: '请填写本次加油金额!' }]}
            >
              <InputNumber style={{width: '80%'}}></InputNumber>
            </FormItem>

            <FormItem
              {...formItemLayout}
              label="卡内余额(元)"
              name="kaneiyue"
            >
              <InputNumber style={{width: '80%'}}></InputNumber>
            </FormItem>

            <FormItem
              {...formItemLayout}
              label="归还时间"
              name="guihuanshijian"
            >
              <DatePicker style={{width: '80%'}}/>
            </FormItem>
            <FormItem
              {...formItemLayout}
              label="加油时间"
              name="jiayoushijian"
              rules={[{ required: true, message: '请填写加油时间!' }]}
            >
              <DatePicker style={{width: '80%'}}/>
            </FormItem>
            <FormItem
              {...formItemLayout}
              label="是否上交小票"
              name="shifoushangjiaoxiaopiao"
            >
              <Select style={{width: '80%'}}>
                <Option value={"否"}>否</Option>
                <Option value={"是"}>是</Option>
              </Select>
            </FormItem>
            <FormItem
              {...formItemLayout}
              label="驾驶员签字"
              name="jiashiyanname"
            >
              <Input style={{width: '80%'}}></Input>
            </FormItem>
          </Col>
        </Row>


      </Form>
    </Modal>
  )
}

export default JykrcgldjModal ;
