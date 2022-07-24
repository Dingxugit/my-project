import React, {useState} from 'react';

import {Form, Input, InputNumber, message, Modal, Select,Row,Col,DatePicker} from 'antd';
import {getLastGlsBycarnum, save, update} from "@/services/ylshh/dckp";
import {CarInfo} from '@/components/gy';
import moment from 'moment' ;

const FormItem = Form.Item;
const {Option}= Select ;

const formItemLayout = {
  labelCol: { span: 6 },
  wrapperCol: { span: 18 },
};

const DcyhkpModal = (props) =>{

  const { modalVisible, onSubmit, onCancel,dcId ,values ,defValue} = props;

  const [form] = Form.useForm();
  const [loading, handleLoading] = useState(false);
  const [yearV,handleYearV] = useState(moment(new Date(),"yyyy")) ;

  const okHandle = async () => {
    handleLoading(true);
    const id = dcId ;
    const fieldsValue = await form.validateFields();
    let data = null;
    fieldsValue.year = fieldsValue.year.format("yyyy");
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

  const getLastGls =(e) =>{
    if (yearV === undefined || yearV ===null) {
      message.error("请先输入年份");
    }
    getLastGlsBycarnum({numberplate:e,year:moment(yearV).format("yyyy")}).then((res) =>{
      if(res.result){
        form.setFieldsValue({
          lichengchudu: res.result.lichengweidu === null? 0 : res.result.lichengweidu,
          xinghao:res.result.cpxx
        });
      }else{
        form.setFieldsValue({
          lichengchudu: 0,
          xinghao:null
        });
      }
    })

  }

  const handleLc =() =>{
    const lccd = form.getFieldValue("lichengchudu");
    const lcmd = form.getFieldValue("lichengweidu");
    form.setFieldsValue({
      xingshilicheng: lcmd -lccd
    });
  }

  const handleZxbz = () =>{
    const hyl = form.getFieldValue("haoyouliang");
    const xslc = form.getFieldValue("xingshilicheng");
    form.setFieldsValue({
      zhixingbiaozhun: Math.round(100 * hyl/xslc *100)/100
    });
  }

  const selectDate = (v) =>{
    handleYearV(v);

    if (form.getFieldValue("numberplate") !== undefined && form.getFieldValue("numberplate") !== null){
      getLastGlsBycarnum({numberplate:form.getFieldValue("numberplate"),year:form.getFieldValue("year")}).then((res) =>{
        if(res.result){
          form.setFieldsValue({
            lichengchudu: res.result.lichengweidu === null? 0 : res.result.lichengweidu,
            xinghao:res.result.cpxx
          });
        }else{
          form.setFieldsValue({
            lichengchudu: 0,
            xinghao:null
          });
        }
      })
    }
  }

  return (
    <Modal
      confirmLoading={loading}
      destroyOnClose
      title={dcId ? '修改单车油耗卡片信息' : '新建单车油耗卡片信息'}
      visible={modalVisible}
      onOk={okHandle}
      onCancel={onCancel}
      width={1000}
    >
      <Form
        initialValues={{
          year: values.year ? moment(values.year,"yyyy") : moment(new Date(),"yyyy")
          ,yuefen: values.yuefen
          ,numberplate: values.numberplate
          , youpin: values.youpin
          , leixing: values.leixing
          , pinpai: values.pinpai
          , pailiang: values.pailiang
          , xinghao: values.xinghao
          , youhaobiaozhun: values.youhaobiaozhun
          , zhixingbiaozhun: values.zhixingbiaozhun
          , lichengchudu: values.lichengchudu
          , lichengweidu: values.lichengweidu
          , xingshilicheng: values.xingshilicheng
          , motuoxiaoshi: values.motuoxiaoshi
          , haoyouliang: values.haoyouliang
          , qianziname: values.qianziname
        }}
        form={form}
      >
        <Row>

       <Col span={12}>
         <FormItem
           {...formItemLayout}
           label="年份"
           name="year"
           rules={[{ required: true, message: '请填写年份' }]}
         >
           <DatePicker picker="year" onChange={selectDate} defaultValue={moment(new Date(),"yyyy")} format="yyyy" style={{width: '80%'}} />
         </FormItem>

        <FormItem
          {...formItemLayout}
          label="月份"
          name="yuefen"
          rules={[{ required: true, message: '请填写月份!' }]}
        >
          <Select style={{width: '80%'}}>
            <Option value={"1"}>1月</Option>
            <Option value={"2"}>2月</Option>
            <Option value={"3"}>3月</Option>
            <Option value={"4"}>4月</Option>
            <Option value={"5"}>5月</Option>
            <Option value={"6"}>6月</Option>
            <Option value={"7"}>7月</Option>
            <Option value={"8"}>8月</Option>
            <Option value={"9"}>9月</Option>
            <Option value={"10"}>10月</Option>
            <Option value={"11"}>11月</Option>
            <Option value={"12"}>12月</Option>
          </Select>
        </FormItem>

        <FormItem
          {...formItemLayout}
          label="车牌号"
          name="numberplate"
          rules={[{ required: true, message: '请填写车牌号!' }]}
        >
          <CarInfo onChange={getLastGls}/>
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
          label="类型"
          name="leixing"
          rules={[{ required: true, message: '请填写类型' }]}
        >
          <Input style={{width: '80%'}}></Input>
        </FormItem>
        <FormItem
          {...formItemLayout}
          label="品牌"
          name="pinpai"
          rules={[{ required: true, message: '请填写品牌!' }]}
        >
          <Input style={{width: '80%'}}></Input>
        </FormItem>

        <FormItem
          {...formItemLayout}
          label="排量"
          name="pailiang"
          rules={[{ required: true, message: '请填写排量!' }]}
        >
          <Input style={{width: '80%'}}></Input>
        </FormItem>

        <FormItem
          {...formItemLayout}
          label="型号"
          name="xinghao"
          rules={[{ required: true, message: '请填写型号!' }]}
        >
          <Input style={{width: '80%'}}></Input>
        </FormItem>

       </Col>
          <Col span={12}>

        <FormItem
          {...formItemLayout}
          label="消耗标准"
          name="youhaobiaozhun"
        >
          <InputNumber style={{width: '80%'}}></InputNumber>
        </FormItem>

        <FormItem
          {...formItemLayout}
          label="里程初读数"
          name="lichengchudu"
          rules={[{ required: true, message: '请填写里程初读数!' }]}
        >
          <InputNumber style={{width: '80%'}} onChange={handleLc}></InputNumber>
        </FormItem>
        <FormItem
          {...formItemLayout}
          label="里程末读数"
          name="lichengweidu"
          rules={[{ required: true, message: '请填写里程末读数!' }]}
        >
          <InputNumber style={{width: '80%'}} onChange={handleLc}></InputNumber>
        </FormItem>
        <FormItem
          {...formItemLayout}
          label="行驶里程"
          name="xingshilicheng"
        >
          <Input style={{width: '80%'}}  disabled={true} onChange={handleZxbz}></Input>
        </FormItem>
        <FormItem
          {...formItemLayout}
          label="耗油量"
          name="haoyouliang"
          rules={[{ required: true, message: '请填写里程耗油量!' }]}
        >
          <InputNumber style={{width: '80%'}} onChange={handleZxbz}></InputNumber>
        </FormItem>

        <FormItem
          {...formItemLayout}
          label="执行标准"
          name="zhixingbiaozhun"
          rules={[{ required: true, message: '请填写执行标准!' }]}
        >
          <Input style={{width: '80%'}} disabled={true}></Input>
        </FormItem>
        <FormItem
          {...formItemLayout}
          label="摩托小时"
          name="motuoxiaoshi"
        >
          <InputNumber style={{width: '80%'}}></InputNumber>
        </FormItem>

        <FormItem
          {...formItemLayout}
          label="确认签字"
          name="qianziname"
        >
          <Input style={{width: '80%'}}></Input>
        </FormItem>
          </Col>
        </Row>
      </Form>
    </Modal>
  )
}

export default DcyhkpModal ;
