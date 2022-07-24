import React ,{useState} from 'react' ;

import {Form, Input, InputNumber, message, Modal,DatePicker,Select} from 'antd';
import {save, update,getLastCzpzBycarnum} from "@/services/ylshh/jygl";
import {CarInfo} from '@/components/gy' ;
import moment from 'moment';


const FormItem = Form.Item;
const {Option}= Select ;

const formItemLayout = {
  labelCol: { span: 6 },
  wrapperCol: { span: 18 },
};

const JyczpzModal = (props) =>{
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
    fieldsValue.jiayousj = fieldsValue.jiayousj.format(dateFormat) ;
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

  // const validatorUserName = (rule, value, callback) => {
  //   if (value) {
  //     dispatch({
  //       type: 'SysUserModel/checkUserNameEffect',
  //       payload: { id: user.id, userName: value}
  //     }).then((result) => {
  //       if (result) callback();
  //       else callback("用户登陆名称已被使用");
  //     });
  //   } else {
  //     callback();
  //   }
  // };

  const onChangeAndgetGls =(e) =>{
    getLastCzpzBycarnum({numberplate:e}).then((res) =>{
     if(res.result){
      form.setFieldsValue({
        shangciyibiaoshu: res.result.benciyibiaoshu
      });
    }else{
       form.setFieldsValue({
         shangciyibiaoshu: 0
       });
     }
    })

  }

  return (
    <Modal
      confirmLoading={loading}
      destroyOnClose
      title={jyId ? '修改油料社会化保障加油凭证信息' : '新建油料社会化保障加油凭证信息'}
      visible={modalVisible}
      onOk={okHandle}
      onCancel={onCancel}
    >
      <Form
        initialValues={{
          jiayousj: values.jiayousj ? moment(values.jiayousj) : moment(new Date())
          ,numberplate: values.numberplate
          , youpin: values.youpin
          , shuliang: values.shuliang
          , yue: values.yue
          , jingbanren: values.jingbanren
          , shangciyibiaoshu:  values.shangciyibiaoshu
          , benciyibiaoshu: values.benciyibiaoshu}}
        form={form}
      >
        <FormItem
          {...formItemLayout}
          label="加油时间"
          name="jiayousj"
          rules={[{ required: true, message: '请填写加油时间!' }]}
        >
          <DatePicker style={{width: '80%'}}/>
        </FormItem>

        <FormItem
          {...formItemLayout}
          label="车牌号"
          name="numberplate"
          rules={[{ required: true, message: '请填写车牌号!' }]}
        >
          <CarInfo onChange={onChangeAndgetGls}/>
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
          label="数量(公斤)"
          name="shuliang"
          rules={[{ required: true, message: '请填写数量(公斤)!' }]}
        >
          <InputNumber style={{width: '80%'}}></InputNumber>
        </FormItem>

        <FormItem
          {...formItemLayout}
          label="卡内余额"
          name="yue"
        >
          <InputNumber style={{width: '80%'}}></InputNumber>
        </FormItem>

        <FormItem
          {...formItemLayout}
          label="经办人"
          name="jingbanren"

        >
          <Input style={{width: '80%'}}></Input>
        </FormItem>

        <FormItem
          {...formItemLayout}
          label="上次仪表公里数"
          name="shangciyibiaoshu"

        >
          <InputNumber style={{width: '80%'}}></InputNumber>
        </FormItem>

        <FormItem
          {...formItemLayout}
          label="本次仪表公里数"
          name="benciyibiaoshu"
        >
          <InputNumber style={{width: '80%'}}></InputNumber>
        </FormItem>

      </Form>
    </Modal>
  )
}

export default JyczpzModal ;
