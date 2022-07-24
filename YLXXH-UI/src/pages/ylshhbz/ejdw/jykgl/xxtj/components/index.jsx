import React, {useState} from 'react';
import {Form, Input, message, Modal,DatePicker,Row,Col,Upload} from 'antd';
import {save, update} from "@/services/ylshh/jykgl/xxtj";
import {CarInfo} from '@/components/gy';
import moment from 'moment' ;
import styles from "@/pages/sys/user/components/form/index.less";
import {getAccessToken} from "@/utils/token";
import { PlusOutlined, } from '@ant-design/icons';


const FormItem = Form.Item;

const formItemLayout = {
  labelCol: { span: 5 },
  wrapperCol: { span: 19 },
};

function getBase64(file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => resolve(reader.result);
    reader.onerror = error => reject(error);
  });
}

const ShhJykxxtjModal = (props) =>{
  const dateFormat = 'YYYY-MM-DD';
  const { modalVisible, onSubmit, onCancel,tjId ,values } = props;

  // console.log(statement) ;
  const [form] = Form.useForm();

  const [loading, handleLoading] = useState(false);

  const [fileList, setFileList] = useState([
    {
      uid: '-1',
      name: 'image.png',
      status: 'done',
      url: 'https://zos.alipayobjects.com/rmsportal/jkjgkEfvpUPVyRjUImniVslZfWPnJuuZ.png',
    },
  ]);


  const okHandle = async () => {
    handleLoading(true);
    const id = tjId ;
    const fieldsValue = await form.validateFields();
    let data = null;
    fieldsValue.tianbaoshijian = fieldsValue.tianbaoshijian.format(dateFormat) ;
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

  const handleUploadChange = ({ fileList: info }) => {
    // console.info(info)
    form.setFieldsValue({photo: ''})

    // console.info(info)
    if (info.file && info.file.response) {
      const data = info.file.response

      if (data.isSuccess) {
        form.setFieldsValue({photo: data.result})
        setFileList(info);
      } else {
        message.error('上传用户头像失败!');
      }
    }
  }

  const beforeUpload = (file) => {
    const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/jpg';
    if (!isJpgOrPng) {
      message.error('请上传JPG格式图片!');
    }
    const isLt2M = file.size / 1024 / 1024 < 2;
    if (!isLt2M) {
      message.error('请上传2M内的图片!');
    }
    return isJpgOrPng && isLt2M;
  }

  const onPreview = async file => {
    let src = file.url;
    if (!src) {
      src = await new Promise(resolve => {
        const reader = new FileReader();
        reader.readAsDataURL(file.originFileObj);
        reader.onload = () => resolve(reader.result);
      });
    }
    const image = new Image();
    image.src = src;
    const imgWindow = window.open(src);
    imgWindow.document.write(image.outerHTML);
  };

  return (
    <Modal
      confirmLoading={loading}
      destroyOnClose
      title={tjId ? '修改加油卡统计信息' : '新建加油卡统计信息'}
      visible={modalVisible}
      onOk={okHandle}
      onCancel={onCancel}
      width={1300}
    >
      <Form
        initialValues={{
          jiayoukahao: values.jiayoukahao
          ,numberplate: values.numberplate
          , changpaixinghao: values.changpaixinghao
          , fadongjihao: values.fadongjihao
          , chejiahaoma: values.chejiahaoma
          , shengyuzhibiao: values.shengyuzhibiao
          , querequanzu: values.querequanzu
          , tianbaoshijian: values.tianbaoshijian ? moment(values.tianbaoshijian) : moment(new Date())}}
        form={form}
      >
        <Row>
          <Col span={8}>
            <FormItem
              noStyle
              shouldUpdate={(prevValues, currentValues) => prevValues.photo !== currentValues.photo}
            >
              {({ getFieldValue }) => {
                return (
                  <FormItem name="photo">
                    <Upload
                      listType="picture-card"
                      className={styles.avatarUploader}
                      showUploadList={false}
                      action="/zxHealth/t/shh/jytj/uploadPhoto"
                      headers={{Authorization: `Bearer ${getAccessToken()}`}}
                      beforeUpload={beforeUpload}
                      onChange={handleUploadChange}
                      fileList={fileList}
                      onPreview={onPreview}
                    >
                      {
                        getFieldValue('photo') ?
                          <img src={form.getFieldValue('photo')} alt="avatar" /> :
                          <div> <PlusOutlined /> <div className="ant-upload-text">上传车辆图片</div>  </div>
                      }
                    </Upload>
                  </FormItem>
                )
              }}
            </FormItem>
          </Col>

        <Col span={16}>
        <FormItem
          {...formItemLayout}
          label="加油卡卡号"
          name="jiayoukahao"
          rules={[{ required: true, message: '请填写加油卡卡号!' }]}
        >
          <Input></Input>
        </FormItem>

        <FormItem
          {...formItemLayout}
          label="车牌号"
          name="numberplate"
          rules={[{ required: true, message: '请填写车牌号!' }]}
        >
          <CarInfo />
        </FormItem>

        <FormItem
          {...formItemLayout}
          label="填报时间"
          name="tianbaoshijian"
          rules={[{ required: true, message: '请填写填报时间!' }]}

        >
          <DatePicker />
        </FormItem>

        <FormItem
          {...formItemLayout}
          label="剩余指标"
          name="shengyuzhibiao"
          rules={[{ required: true, message: '请填写剩余指标!' }]}
        >
          <Input></Input>
        </FormItem>

        <FormItem
          {...formItemLayout}
          label="确认签字"
          name="querequanzu"
          rules={[{ required: true, message: '请填写确认签字!' }]}
        >
          <Input></Input>
        </FormItem>
        </Col>
        </Row>
      </Form>
    </Modal>
  )
}

export default ShhJykxxtjModal ;
